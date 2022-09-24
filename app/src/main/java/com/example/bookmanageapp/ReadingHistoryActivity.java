package com.example.bookmanageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;
import com.example.bookmanageapp.featureclass.ReadingHistory;
import com.example.bookmanageapp.utils.UseLog;
import com.example.bookmanageapp.view.ReadingHistoryListAdapter;

import java.util.ArrayList;

public class ReadingHistoryActivity extends BasementActivity {

    private ListView mReadingHistoryList;

    DBHelper mDBHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading_history);
        if (!getUserAccount().isLogin(getApplicationContext())) {
            UseLog.i("logout status");
            // send it to login activity
        }

        mReadingHistoryList = findViewById(R.id.listview_reading_history_list);

        mDBHelper = new DBHelper(getApplicationContext());
        ArrayList<ReadingHistory> userReadingHistoryList = DBQuery.findReadingHistoryList(mDBHelper, getUserAccount());
        UseLog.i(String.valueOf(userReadingHistoryList.size()));
        ReadingHistoryListAdapter readingHistoryListAdapter = new ReadingHistoryListAdapter(getApplicationContext(), userReadingHistoryList);
        mReadingHistoryList.setAdapter(readingHistoryListAdapter);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (getUserAccount().isLogin(getApplicationContext())) {
            MenuItem item = menu.findItem(R.id.action_reading_history);
            item.setVisible(false);
        }
        return true;
    }
}