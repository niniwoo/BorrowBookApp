package com.example.bookmanageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.UserAccount;
import com.example.bookmanageapp.utils.ConstantValue;
import com.example.bookmanageapp.utils.UseLog;

public class LogInActivity extends BasementActivity {

    private EditText mIdText;
    private EditText mPwText;
    private Button mSignInBtn;
    private Button mSignUpBtn;

    private DBHelper mDbHelper;
//    private int mClickCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mIdText = findViewById(R.id.edit_login_id);
        mPwText = findViewById(R.id.edit_login_pw);
        mSignInBtn = findViewById(R.id.btn_login_sign_in);
        mSignUpBtn = findViewById(R.id.btn_login_sign_up);

        mSignInBtn.setOnClickListener(btnClickListener);
        mSignUpBtn.setOnClickListener(btnClickListener);

        // change the way to AdminActivity. This way is disappeared (committed at 26Mar2022)
//        findViewById(R.id.layout_hidden_menu_btn).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (mClickCount > 0) {
//                    Toast.makeText(getApplicationContext(), mClickCount + " " + getApplicationContext().getResources().getString(R.string.toast_clicks_left), Toast.LENGTH_SHORT).show();
//                    mClickCount--;
//                } else {
//                    Intent uIntent = new Intent(getApplicationContext(), AdminActivity.class);
//                    startActivity(uIntent);
//                }
//            }
//        });
    }

    @Override
    protected void onResume() {
        super.onResume();
//        mClickCount = 5;
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

    View.OnClickListener btnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btn_login_sign_in) {
                // run login code
                mDbHelper = new DBHelper(getApplicationContext());
                UserAccount loginUA = DBQuery.findUserInUSERS(mDbHelper, new UserAccount(mIdText.getText().toString(), mPwText.getText().toString()));
                if (loginUA == null) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.toast_cannot_find_id), Toast.LENGTH_LONG).show();
                } else {
                    setUserAccount(loginUA);
                    getUserAccount().tryLogin(getApplicationContext());
                    setResult(Activity.RESULT_OK);
                    finish();
                }
            } else if (view.getId() == R.id.btn_login_sign_up) {
                // go to sign up activity
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(intent);
            }
        }
    };
}
