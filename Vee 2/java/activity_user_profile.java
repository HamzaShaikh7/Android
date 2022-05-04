package com.example.vee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class activity_user_profile extends AppCompatActivity
{

    AlertDialog.Builder builder;
    TextView user_id, user_name, user_email;
    String Staring_userid, String_username, String_email;
    DatabaseReference reference;
    BottomNavigationView bottomNavigationView;

    Send_Fragment send_fragment;
    Wallet_Fragment wallet_fragment;
    Account_Fragment account_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        user_id = findViewById(R.id.user_id);
        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        bottomNavigationView = findViewById(R.id.bottom_navigation);

        send_fragment = new Send_Fragment();
        wallet_fragment = new Wallet_Fragment();
        account_fragment = new Account_Fragment();

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


        if(null != activeNetwork)
        {
            //Load all data of user...
            LoadAllDate();
        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }

    }

    private void LoadAllDate()
    {

        SharedPreferences sharedPreferences = getSharedPreferences("user_data",MODE_PRIVATE);
        String user_id_shared = sharedPreferences.getString("user_id","");

        reference = FirebaseDatabase.getInstance("https://vee2-a7bb5-default-rtdb.firebaseio.com/").getReference("user");
        Query checkUser = reference.orderByChild("userId").equalTo(user_id_shared);


        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                if(snapshot.exists())
                {
                    Staring_userid = user_id_shared;
                    String_username = snapshot.child(user_id_shared).child("uname").getValue(String.class);
                    String_email = snapshot.child(user_id_shared).child("email").getValue(String.class);

                    user_id.setText(Staring_userid);
                    user_name.setText(String_username);
                    user_email.setText(String_email);

                }
                else
                {
                    Toast.makeText(activity_user_profile.this, "Please restart application...", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

                Toast.makeText(activity_user_profile.this, "Please restart application...", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void logout_function()
    {
        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();


        if(null != activeNetwork)
        {
            Intent intent = new Intent(getApplicationContext(), login_activity.class);

            SharedPreferences sharedPreferences = getSharedPreferences("user_data", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();

            editor.putString("user_id", "");
            editor.putString("user_password", "");
            editor.apply();

            startActivity(intent);
            finish();
        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public void Logout(View view) {

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null != activeNetwork) {

            // Create the object of
            // AlertDialog Builder class
            builder
                    = new AlertDialog
                    .Builder(this);

            // Set the message show for the Alert time
            builder.setMessage("Do you want to logout ?");

            // Set Alert Title
            builder.setTitle("Logout");

            // Set Cancelable false
            // for when the user clicks on the outside
            // the Dialog Box then it will remain show
            builder.setCancelable(false);

            // Set the positive button with yes name
            // OnClickListener method is use of
            // DialogInterface interface.

            builder
                    .setPositiveButton(
                            "Yes",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog,
                                                    int id) {

                                    // When the user click yes button
                                    // then app will close
                                    logout_function();
                                }
                            });

            // Set the Negative button with No name
            // OnClickListener method is use
            // of DialogInterface interface.
            builder
                    .setNegativeButton(
                            "No",
                            new DialogInterface
                                    .OnClickListener() {

                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                    // If user click no
                                    // then dialog box is canceled.
                                    dialog.cancel();
                                }
                            });

            // Create the Alert dialog
            AlertDialog alertDialog = builder.create();

            // Show the Alert Dialog box
            alertDialog.show();
        } else {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}