package com.example.bookmanageapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintSet;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.UserAccount;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

public class UserInfoActivity extends BasementActivity {

    private EditText mUserIdEdit;
    private EditText mUserPwEdit;
    private EditText mUserNameEdit;
    private EditText mUserAgeEdit;
    private EditText mUserAddrEdit;
    private EditText mUserGenreEdit;
    private Button mSubmitBtn;
    private Button mCheckIDBtn;

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        if (!getUserAccount().isLogin(getApplicationContext())) {
            UseLog.i("logout status");
            // send it to login activity
        }

        mDBHelper = new DBHelper(getApplicationContext());

        mUserIdEdit = findViewById(R.id.edit_user_info_id);
        mUserPwEdit = findViewById(R.id.edit_user_info_pw);
        mUserNameEdit = findViewById(R.id.edit_user_info_name);
        mUserAgeEdit = findViewById(R.id.edit_user_info_age);
        mUserAddrEdit = findViewById(R.id.edit_user_info_addr);
        mUserGenreEdit = findViewById(R.id.edit_user_info_genre);
        mSubmitBtn = findViewById(R.id.btn_user_info_submit);
        mCheckIDBtn = findViewById(R.id.btn_edit_user_info_id_check);

        mSubmitBtn.setOnClickListener(mSubmitBtnClickListener);
        mCheckIDBtn.setOnClickListener(mCheckBtnClickListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getUserAccount().isLogin(getApplicationContext())) {
            MenuItem item = menu.findItem(R.id.action_update_user_info);
            item.setVisible(false);
        }
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // layout redraw
        if (getUserAccount().isLogin(getApplicationContext())) {
            // update user info
            mCheckIDBtn.setVisibility(View.GONE);
            mUserIdEdit.setEnabled(false);

            UserAccount ua = DBQuery.findUserInUSERS(mDBHelper, getUserAccount());
            mUserIdEdit.setText(ua.getId());
            mUserPwEdit.setText(ua.getPassword());
            mUserNameEdit.setText(ua.getName());
            mUserAgeEdit.setText(String.valueOf(ua.getAge()));
            mUserAddrEdit.setText(ua.getAddress());
            mUserGenreEdit.setText(ua.getGenre());
        }

        mUserNameEdit.requestFocus();
    }

    View.OnClickListener mSubmitBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            if (mUserIdEdit.isEnabled()) {
                // insert new user account process
                String newID = mUserIdEdit.getText().toString();
                String newPw = mUserPwEdit.getText().toString();
                String newName = mUserNameEdit.getText().toString();
                String newAge = mUserAgeEdit.getText().toString();
                String newAddr = mUserAddrEdit.getText().toString();
                String newGenre = mUserGenreEdit.getText().toString();

                if (newID.equals("") || newPw.equals("") || newName.equals("") | newAge.equals("") || newAddr.equals("") || mUserGenreEdit.equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_input_data_is_wrong), Toast.LENGTH_LONG).show();
                    return;
                }

                UserAccount ua = new UserAccount(newID, newPw, newName, Integer.parseInt(newAge), newAddr, newGenre, false);
                if (DBQuery.checkUserIDInUSERS(mDBHelper, ua)) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_id_already_used), Toast.LENGTH_LONG).show();
                    return;
                }

                long result = DBQuery.insertUserToUSERS(mDBHelper, ua);
                if (result > 0) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_create_new_account), Toast.LENGTH_LONG).show();
                    finish();
                }

            } else {
                // update user account process
                String newPw = mUserPwEdit.getText().toString();
                String newName = mUserNameEdit.getText().toString();
                String newAge = mUserAgeEdit.getText().toString();
                String newAddr = mUserAddrEdit.getText().toString();
                String newGenre = mUserGenreEdit.getText().toString();

                if (newPw.equals("") || newName.equals("") | newAge.equals("") || newAddr.equals("") || mUserGenreEdit.equals("")) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_input_data_is_wrong), Toast.LENGTH_LONG).show();
                    return;
                }

                UserAccount ua = new UserAccount(getUserAccount().getId(), newPw, newName, Integer.parseInt(newAge), newAddr, newGenre, false);
                int result = DBQuery.updateUserToUSERS(mDBHelper, ua);
                if (result > 0) {
                    setUserAccount(ua);
                    getUserAccount().tryLogin(getApplicationContext());
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_succeed_to_update_your_account), Toast.LENGTH_LONG).show();
                    finish();
                }
            }
        }
    };

    View.OnClickListener mCheckBtnClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            String newID = mUserIdEdit.getText().toString();
            String newPw = mUserPwEdit.getText().toString();

            UserAccount ua = new UserAccount(newID, newPw);
            if (DBQuery.checkUserIDInUSERS(mDBHelper, ua)) {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_id_already_used), Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_this_id_can_use), Toast.LENGTH_LONG).show();
            }
        }
    };
}