package com.example.vee2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class forget_password_activity extends AppCompatActivity {

    TextInputEditText user_id;
    ProgressBar forget_progressbar;
    TextView user_manual;
    DatabaseReference reference1, reference2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        user_id = findViewById(R.id.user_id);
        forget_progressbar = findViewById(R.id.forget_progressbar);
        user_manual = findViewById(R.id.user_manual);

    }

    public void ForgetPasswor(View view)
    {
        String userID = user_id.getText().toString().trim();

        user_manual.setVisibility(View.INVISIBLE);

        ConnectivityManager manager = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = manager.getActiveNetworkInfo();

        if (null != activeNetwork) {

            forget_progressbar.setVisibility(View.VISIBLE);

            DatabaseReference reference = FirebaseDatabase.getInstance("https://vee2-a7bb5-default-rtdb.firebaseio.com/").getReference("user");
            Query checkUser = reference.orderByChild("userId").equalTo(userID);

            checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot)
                {

                    if(snapshot.exists())
                    {

                        reference2 = FirebaseDatabase.getInstance("https://vee2-a7bb5-default-rtdb.firebaseio.com/").getReference("PasswordRequest");

                        UserHeplerClass2 userHeplerClass2 = new UserHeplerClass2(userID, "Password");

                        reference2.child(userID).setValue(userHeplerClass2);
                        forget_progressbar.setVisibility(View.INVISIBLE);
                        user_manual.setVisibility(View.VISIBLE);

                    }
                    else
                    {
                        user_id.setError("No ID found");
                        user_id.requestFocus();
                        forget_progressbar.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    forget_progressbar.setVisibility(View.INVISIBLE);

                }
            });
        }
        else
        {
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_SHORT).show();
            forget_progressbar.setVisibility(View.INVISIBLE);
        }
    }

    private boolean validateuserid()
    {
        String val = user_id.getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";

        if (val.isEmpty())
        {
            user_id.setError("Field cannot be empty");
            return false;
        }
        else
        {
            user_id.setError(null);
            user_id.setEnabled(false);
            return true;
        }
    }
}
