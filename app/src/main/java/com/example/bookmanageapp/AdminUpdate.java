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

public class AdminUpdate extends BasementActivity {

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_update);
        dbHelper = new DBHelper(getApplicationContext());

        EditText name = findViewById(R.id.txt_Name);
        EditText age = findViewById(R.id.txt_Age);
        EditText address = findViewById(R.id.txt_Address);
        EditText id = findViewById(R.id.txt_id);
        Button update = findViewById(R.id.update_button);
        DBHelper dbHelper = new DBHelper(this);

        update.setOnClickListener(new View.OnClickListener() {

            String Name, Age, Address, Id;
            boolean isUpdated;

            @Override
            public void onClick(View view) {

                Name = name.getText().toString();
                Age = age.getText().toString();
                Address = address.getText().toString();
                Id = id.getText().toString();

                isUpdated = DBQuery.updateRec(dbHelper, Id, Name, Age, Address);

                if (isUpdated) {
                    Toast.makeText(AdminUpdate.this, "Account is updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminUpdate.this, "Account is not updated", Toast.LENGTH_SHORT).show();
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