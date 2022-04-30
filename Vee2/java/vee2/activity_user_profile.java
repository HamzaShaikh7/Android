package com.example.vee2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class activity_user_profile extends AppCompatActivity
{

    TextView user_id, user_name, user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user_id = findViewById(R.id.user_id);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);

        //Show all data of user...

        showAllDate();

    }

    private void showAllDate()
    {
        Intent intent = getIntent();
        String user_id_intent = intent.getStringExtra("userId");
        String user_name_intent = intent.getStringExtra("uname");
        String user_email_intent = intent.getStringExtra("email");


        user_id.setText(user_id_intent);
        user_name.setText(user_name_intent);
        user_email.setText(user_email_intent);
    }
}