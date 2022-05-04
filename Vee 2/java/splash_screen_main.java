package com.example.vee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class splash_screen_main extends AppCompatActivity {


    // Declaring Variables.
    private static int SPLASH_SCREEN = 1500;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_main);


        // to hide status bar...
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        progressBar = findViewById(R.id.splash_progressbar);

        check_connection();


    }

    public void check_connection()
    {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


        if(null != activeNetwork)
        {

            SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
            String user_id = sharedPreferences.getString("user_id","");
            String user_password = sharedPreferences.getString("user_password","");


            if( user_id.isEmpty() && user_password.isEmpty())
            {
                login();
            }

            else
            {
                isUser(user_id,user_password);
            }


        }
        else
            {
                Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
            }
        }

    private void login()
    {
        // Handling splash screen...
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(splash_screen_main.this, login_activity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }


    private void isUser(String user_id, String user_password)
    {

        progressBar.setVisibility(View.VISIBLE);

        String userID = user_id;
        String userPassword = user_password;

        DatabaseReference reference = FirebaseDatabase.getInstance("https://vee2-a7bb5-default-rtdb.firebaseio.com/").getReference("user");
        Query checkUser = reference.orderByChild("userId").equalTo(userID);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    String PasswordFromDB = snapshot.child(userID).child("upassword").getValue(String.class);

                    if(PasswordFromDB.equals(userPassword))
                    {
                        String userFnameDB = snapshot.child(userID).child("uname").getValue(String.class);
                        String userEmailDB = snapshot.child(userID).child("email").getValue(String.class);


                        Intent intent = new Intent(getApplicationContext(),activity_user_profile.class);

                        intent.putExtra("uname",userFnameDB);
                        intent.putExtra("userId",userID);
                        intent.putExtra("email",userEmailDB);

                        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();

                        editor.putString("user_id",userID);
                        editor.putString("user_password",userPassword);
                        editor.apply();

                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        login();
                    }
                }
                else
                {
                    login();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}