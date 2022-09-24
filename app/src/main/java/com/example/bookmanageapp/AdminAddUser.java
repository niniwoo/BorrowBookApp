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

public class AdminAddUser extends BasementActivity {

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_add_user);
        dbHelper = new DBHelper(getApplicationContext());

        EditText id = findViewById(R.id.editTextID);
        EditText password = findViewById(R.id.editTextPassword);
        EditText name = findViewById(R.id.editTextName);
        EditText age = findViewById(R.id.editTextAge);
        EditText address = findViewById(R.id.editTextAddress);
        EditText genre = findViewById(R.id.editTextGenre);
        Button adduser = findViewById(R.id.addUserButton);

        adduser.setOnClickListener(new View.OnClickListener() {

            String ID, Password, Name, Age, Address, Genre;
            Boolean isInserted;

            @Override
            public void onClick(View view) {
                ID = id.getText().toString();
                Password = password.getText().toString();
                Name = name.getText().toString();
                Age = age.getText().toString();
                Address = address.getText().toString();
                Genre = genre.getText().toString();

                isInserted = DBQuery.addRecord(dbHelper, ID, Password, Name, Age, Address, Genre, false);

                if (isInserted) {
                    Toast.makeText(AdminAddUser.this, "Account is Created", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminAddUser.this, "Account is not Created", Toast.LENGTH_SHORT).show();
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