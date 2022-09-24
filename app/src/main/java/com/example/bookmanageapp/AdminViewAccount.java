package com.example.bookmanageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;
import android.widget.Toast;

import com.amitshekhar.utils.DatabaseHelper;
import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;

//Created by Jeongin
public class AdminViewAccount extends BasementActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view_account);
        TextView showData = findViewById(R.id.txtAdmintViewAccount);
        dbHelper = new DBHelper(this);

        Cursor c = DBQuery.viewAccount(dbHelper);
        StringBuilder str = new StringBuilder();
        if (c.getCount() > 0) {
            while (c.moveToNext()) {
                str.append(" User Id : " + c.getString(0));
                str.append(" User name :" + c.getString(2));
                str.append("\n");

            }
        } else {
            Toast.makeText(AdminViewAccount.this, "Account doesn't exist", Toast.LENGTH_SHORT).show();
        }
        showData.setText(str);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_logout, menu);
        return true;
    }
}