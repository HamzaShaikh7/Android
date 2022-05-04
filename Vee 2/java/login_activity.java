package com.example.vee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class login_activity extends AppCompatActivity {


    Button new_user, forget_password_btn;
    TextInputLayout user_id, user_password;
    ProgressBar login_progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        new_user = findViewById(R.id.new_user);
        forget_password_btn = findViewById(R.id.forget_password);
        user_id = findViewById(R.id.user_id);
        user_password = findViewById(R.id.user_password);
        login_progressBar = findViewById(R.id.login_progressbar);

    }

    private boolean validateuserid()
    {
        String val = user_id.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty())
        {
            user_id.setError("Field cannot be empty");
            return false;
        }
        else
        {
            user_id.setError(null);
            user_id.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatepassword()
    {
        String val = user_password.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty())
        {
            user_password.setError("Field cannot be empty");
            return false;
        }
        else
        {
            user_password.setError(null);
            user_password.setErrorEnabled(false);
            return true;
        }
    }

    public void SingIn(View view)
    {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


        if(null != activeNetwork)
        {

            if(!validateuserid() | !validatepassword())
            {
                return;
            }
            else
            {
                isUser();
            }

        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void isUser()
    {

        login_progressBar.setVisibility(View.VISIBLE);

        String userID = user_id.getEditText().getText().toString().trim();
        String userPassword = user_password.getEditText().getText().toString().trim();

        DatabaseReference reference = FirebaseDatabase.getInstance("https://vee2-a7bb5-default-rtdb.firebaseio.com/").getReference("user");
        Query checkUser = reference.orderByChild("userId").equalTo(userID);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    user_id.setError(null);
                    user_id.setErrorEnabled(false);

                    String PasswordFromDB = snapshot.child(userID).child("upassword").getValue(String.class);

                    if(PasswordFromDB.equals(userPassword))
                    {
                        user_id.setError(null);
                        user_id.setErrorEnabled(false);

                        Intent intent = new Intent(getApplicationContext(),activity_user_profile.class);

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
                        user_password.setError("Wrong Password");
                        user_password.requestFocus();
                        login_progressBar.setVisibility(View.INVISIBLE);

                    }
                }
                else
                {
                    user_id.setError("No ID found");
                    user_id.requestFocus();
                    login_progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                login_progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }

    public void ForgetPasswordbtn(View view)
    {
        login_progressBar.setVisibility(View.INVISIBLE);

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


        if(null != activeNetwork)
        {

            Intent intent_forgetPass = new Intent(login_activity.this, forget_password_activity.class);
            startActivity(intent_forgetPass);

        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void NewUser(View view)
    {
        login_progressBar.setVisibility(View.INVISIBLE);

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


        if(null != activeNetwork)
        {
            Intent intent_signup = new Intent(login_activity.this, sign_up.class);
            startActivity(intent_signup);
            finish();

        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}