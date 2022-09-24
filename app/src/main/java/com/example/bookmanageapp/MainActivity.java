package com.example.bookmanageapp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.ViewPager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.amitshekhar.DebugDB;
import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.BookItem;
import com.example.bookmanageapp.featureclass.UserMessages;
import com.example.bookmanageapp.featureclass.ReadingHistory;
import com.example.bookmanageapp.featureclass.UserAccount;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;
import com.example.bookmanageapp.view.MainViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends BasementActivity {

    private ViewPager mPager;
    private TabLayout mTabLayout;
    private DBHelper mDbHelper;

    private boolean mLoginActivityIsRunning = false;
    private int mViewPagerPage = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPager = findViewById(R.id.main_activity_pager);
        mTabLayout = findViewById(R.id.main_activity_pager_tab_layout);

        mDbHelper = new DBHelper(getApplicationContext());
        mPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mViewPagerPage = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        // code for Database debug
        DebugDB.getAddressLog();

        // add admin account if it is not existed in DB
        checkAdminAccount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        UseLog.i("onResume()");
        if (getUserAccount().isLogin(getApplicationContext())) {
            mLoginActivityIsRunning = false;

            ArrayList<BookItem> ownBookList = DBQuery.findUserOwnBookList(mDbHelper, getUserAccount());
            ArrayList<BookItem> borrowBookList = DBQuery.findBorrowBookList(mDbHelper, getUserAccount());
            ArrayList<BookItem> readingBookLIst = DBQuery.findReadingBookList(mDbHelper, getUserAccount());

            MainViewPagerAdapter mPagerAdapter =
                    new MainViewPagerAdapter(getApplicationContext(), ownBookList, borrowBookList, readingBookLIst);
            mPager.setAdapter(mPagerAdapter);
            mTabLayout.setupWithViewPager(mPager);
            mPager.setCurrentItem(mViewPagerPage);

            // change the way to AdminActivity. This way is created. (committed at 26Mar2022)
            if (getUserAccount().getId().equals(ConstantValue.ADMIN_ACCOUNT_ID)
                    && getUserAccount().getPassword().equals(ConstantValue.ADMIN_ACCOUNT_PASSWORD)) {

                UserAccount adminUA = DBQuery.findUserInUSERS(mDbHelper, getUserAccount());
                if (adminUA.isAdmin()) {
                    mStartForResult.launch(new Intent(this, AdminActivity.class));
                }
            }
        } else {
            if (!mLoginActivityIsRunning) {
                mStartForResult.launch(new Intent(this, LogInActivity.class));
                mLoginActivityIsRunning = true;
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UseLog.i("onDestroy()");
        getUserAccount().tryLogout(getApplicationContext());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_log_out:
                // log out and go to log in screen
                getUserAccount().tryLogout(getApplicationContext());
                UseLog.i("action_log_out");
                onResume();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        UseLog.i("onBackPressed");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(this.getResources().getString(R.string.dialog_end_app_title));
        builder.setMessage(this.getResources().getString(R.string.dialog_end_app_description));
        builder.setIcon(android.R.drawable.ic_dialog_alert);

        builder.setPositiveButton(this.getResources().getString(R.string.dialog_end_app_ok), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        builder.setNegativeButton(this.getResources().getString(R.string.dialog_end_app_cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.setCancelable(true);
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    ActivityResultLauncher<Intent> mStartForResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                switch (result.getResultCode()) {
                    case Activity.RESULT_OK:
                        UseLog.i("Activity.RESULT_OK");
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_login), Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        UseLog.i("Activity.RESULT_CANCELED");
                        mLoginActivityIsRunning = true;
                        finish();
                        break;
                    case ConstantValue.ADMIN_ACTIVITY_RESULT_FINISH:
                    default:
                        break;
                }
            }
    );

    private void checkAdminAccount() {
        UserAccount ua = new UserAccount(ConstantValue.ADMIN_ACCOUNT_ID, ConstantValue.ADMIN_ACCOUNT_PASSWORD);
        if (DBQuery.findUserInUSERS(mDbHelper, ua) == null) {
            DBQuery.insertUserToUSERS(mDbHelper,
                    new UserAccount("admin", "admin", "admin", 99, "New Westminster", "Computer", true));
        }
    }
}