package com.example.bookmanageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.UserMessages;
import com.example.bookmanageapp.utils.UseLog;
import com.example.bookmanageapp.view.UserMessageListAdapter;

import java.util.ArrayList;

public class MessageActivity extends BasementActivity {

    private DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        if (!getUserAccount().isLogin(getApplicationContext())) {
            UseLog.i("logout status");
            // send it to login activity
        };

        ListView lvLayout = findViewById(R.id.listview_message);

        mDBHelper = new DBHelper(getApplicationContext());
        ArrayList<UserMessages> messages = DBQuery.findUserMsgList(mDBHelper, getUserAccount());

        UserMessageListAdapter msgListAdapter = new UserMessageListAdapter(getApplicationContext(), messages);
        lvLayout.setAdapter(msgListAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getUserAccount().isLogin(getApplicationContext())) {
            MenuItem item = menu.findItem(R.id.action_receive_msg);
            item.setVisible(false);
        }
        return true;
    }
}