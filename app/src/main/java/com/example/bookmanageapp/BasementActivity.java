package com.example.bookmanageapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.example.bookmanageapp.featureclass.ReadingHistory;
import com.example.bookmanageapp.featureclass.UserAccount;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

public class BasementActivity extends AppCompatActivity {

    private UserAccount mUserAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUserAccount = new UserAccount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        ActionBar ab = getSupportActionBar();
        if (!getUserAccount().isLogin(getApplicationContext())) {
            ab.setTitle(R.string.need_to_login);
        } else {
            ab.setTitle(getUserAccount().getId());
        }
        invalidateOptionsMenu();


        View view = this.getCurrentFocus();
        if (view != null) {
            UseLog.i("keyboard should be hide");
            InputMethodManager imm = (InputMethodManager)getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public UserAccount getUserAccount() {
        return mUserAccount;
    }

    public void setUserAccount(UserAccount mUserAccount) {
        this.mUserAccount = mUserAccount;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        if (getUserAccount().isLogin(getApplicationContext())) {
            menuInflater.inflate(R.menu.actionbar_login, menu);
        } else {
            menuInflater.inflate(R.menu.actionbar_logout, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_update_user_info:
                // go to update info screen
                if (!getUserAccount().isLogin(getApplicationContext())) {
                    UseLog.i("UA is wrong");
                    return false;
                }
                Intent uIntent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(uIntent);
                if (!getLocalClassName().equals(MainActivity.class.getSimpleName())) {
                    UseLog.i("This activity is not a MainActivity. finish()");
                    finish();
                }
                return true;
            case R.id.action_receive_msg:
                // go to msg list screen
                if (!getUserAccount().isLogin(getApplicationContext())) {
                    UseLog.i("UA is wrong");
                    return false;
                }
                Intent mIntent = new Intent(getApplicationContext(), MessageActivity.class);
                startActivity(mIntent);
                if (!getLocalClassName().equals(MainActivity.class.getSimpleName())) {
                    UseLog.i("This activity is not a MainActivity. finish()");
                    finish();
                }
                return true;
            case R.id.action_reading_history:
                if (!getUserAccount().isLogin(getApplicationContext())) {
                    UseLog.i("UA is wrong");
                    return false;
                }
                Intent rIntent = new Intent(getApplicationContext(), ReadingHistoryActivity.class);
                startActivity(rIntent);
                if (!getLocalClassName().equals(MainActivity.class.getSimpleName())) {
                    UseLog.i("This activity is not a MainActivity. finish()");
                    finish();
                }
                return true;
            case R.id.action_log_out:
                // log out and go to main screen
                getUserAccount().tryLogout(getApplicationContext());
                UseLog.i("action_log_out");
                finish();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}