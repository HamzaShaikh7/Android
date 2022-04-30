package com.example.vee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class sign_up extends AppCompatActivity {


    // Declaring variables...
    TextInputLayout user_name, user_password, user_email, user_referal;
    TextInputEditText user_id;
    Button sign_up;
    ProgressBar signup_progressBar;

    FirebaseDatabase rootNode;
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singn_up);


        // Defining id to variables...
        sign_up = findViewById(R.id.sign_up);

        user_id = findViewById(R.id.ID);
        user_name  = findViewById(R.id.user_name);
        user_password = findViewById(R.id.user_password);
        user_email = findViewById(R.id.user_email);
        user_referal = findViewById(R.id.user_refral);
        signup_progressBar = findViewById(R.id.sign_up_progress);



        // Making unique id based on time...
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMhhddmmyyss");
        String current_date = simpleDateFormat.format(new Date());
        user_id.setText("VEE"+current_date);

    }


    private boolean validateuserfirstName()
    {
        String val = Objects.requireNonNull(user_name.getEditText()).getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty())
        {
            user_name.setError("Field cannot be empty");
            return false;
        }
        else if (val.length()>=15)
        {
            user_name.setError("Value too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace))
        {
            user_name.setError("Space not allowed");
            return false;
        }
        else
        {
            user_name.setError(null);
            user_name.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateuserpassword()
    {
        String val = Objects.requireNonNull(user_password.getEditText()).getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty())
        {
            user_password.setError("Field cannot be empty");
            return false;
        }
        else if (val.length()>=15)
        {
            user_password.setError("Value too long");
            return false;
        }
        else if (!val.matches(noWhiteSpace))
        {
            user_password.setError("Space not allowed");
            return false;
        }
        else
        {
            user_password.setError(null);
            user_password.setErrorEnabled(false);
            return true;
        }
    }


    private boolean validateuseremail()
    {
        String val = Objects.requireNonNull(user_email.getEditText()).getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (val.isEmpty()) {
            user_email.setError("Field cannot be empty");
            return false;
        }
        else if (!val.matches(emailPattern))
        {
            user_email.setError("Invalid email address");
            return false;
        }
        else
        {
            user_email.setError(null);
            return true;
        }
    }


    public void SingUp(View view)
    {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


        if(null != activeNetwork)
        {

            rootNode = FirebaseDatabase.getInstance("https://vee2-a7bb5-default-rtdb.firebaseio.com/");
            reference = rootNode.getReference("user");


            if (!validateuserfirstName() | !validateuserpassword() | !validateuseremail())
            {
                return;
            }

            else
            {

                signup_progressBar.setVisibility(View.VISIBLE);

                // Getting all the values from the text fields.
                String string_user_id = Objects.requireNonNull(user_id.getText()).toString();
                String string_user_name = Objects.requireNonNull(user_name.getEditText()).getText().toString();
                String string_user_password = Objects.requireNonNull(user_password.getEditText()).getText().toString();
                String string_email = Objects.requireNonNull(user_email.getEditText()).getText().toString();
                String string_referal = Objects.requireNonNull(user_referal.getEditText()).getText().toString();

                UserHeplerClass helperClass = new UserHeplerClass(string_user_id, string_user_name, string_user_password, string_email, string_referal);

                reference.child(string_user_id).setValue(helperClass);

                Login();
            }

        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void Login()
    {


        String userID = user_id.getText().toString().trim();
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

                    String PasswordFromDB = snapshot.child(userID).child("upassword").getValue(String.class);

                    if(PasswordFromDB.equals(userPassword))
                    {
                        user_id.setError(null);


                        String userFnameDB = snapshot.child(userID).child("uname").getValue(String.class);
                        String userEmailDB = snapshot.child(userID).child("email").getValue(String.class);


                        Intent intent = new Intent(getApplicationContext(),activity_user_profile.class);

                        intent.putExtra("uname",userFnameDB);
                        intent.putExtra("userId",userID);
                        intent.putExtra("email",userEmailDB);

                        startActivity(intent);
                        finish();
                    }
                    else
                    {
                        user_password.setError("Wrong Password");
                        user_password.requestFocus();
                        signup_progressBar.setVisibility(View.INVISIBLE);

                    }
                }
                else
                {
                    user_id.setError("No ID found");
                    user_id.requestFocus();
                    signup_progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                signup_progressBar.setVisibility(View.INVISIBLE);
            }
        });


    }
}