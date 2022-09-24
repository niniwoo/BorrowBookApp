package com.example.bookmanageapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.bookmanageapp.database.DBHelper;
import com.example.bookmanageapp.database.DBQuery;

//Created by Jeongin
public class AdminDeleteAccount extends BasementActivity {
    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_delete_account);
        dbHelper = new DBHelper(getApplicationContext());

        EditText userId = findViewById(R.id.txtIdtoDelete);
        Button delete = findViewById(R.id.btnAdminDeleteActivity);
        dbHelper = new DBHelper(this);

        delete.setOnClickListener(new View.OnClickListener() {
            String id;
            boolean isDeleted;

            @Override
            public void onClick(View v) {
                id = userId.getText().toString();
                isDeleted = DBQuery.deleteAccount(dbHelper, id);

                if (isDeleted) {
                    Toast.makeText(AdminDeleteAccount.this, "Account is deleted", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminDeleteAccount.this, "Account is not deleted", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.actionbar_logout, menu);
        return true;
    }
}