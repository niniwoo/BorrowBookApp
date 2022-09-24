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

public class AdminMessage extends BasementActivity {

    DBHelper dbHelper = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_message);
        dbHelper = new DBHelper(getApplicationContext());

        EditText id = findViewById(R.id.editTextMessageID);
        EditText message = findViewById(R.id.editTextMessage);
        Button send = findViewById(R.id.btnSendMessage);

        send.setOnClickListener(new View.OnClickListener() {

            String ID, Message;
            Boolean isSent;

            @Override
            public void onClick(View view) {
                ID = id.getText().toString();
                Message = message.getText().toString();

                isSent = DBQuery.addMessage(dbHelper, ID, Message);

                if (isSent) {
                    Toast.makeText(AdminMessage.this, "Message is Sent", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AdminMessage.this, "Message is Not Sent", Toast.LENGTH_SHORT).show();
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