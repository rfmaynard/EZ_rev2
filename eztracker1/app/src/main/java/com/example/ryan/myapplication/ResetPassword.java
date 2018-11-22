package com.example.ryan.myapplication;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ResetPassword extends AppCompatActivity {

    private EditText reset_email;
    private Button verify_email;
    private FirebaseAuth firebase_auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        reset_email = findViewById(R.id.reset_email);
        verify_email = findViewById(R.id.reset_verify);

        firebase_auth = FirebaseAuth.getInstance();

        verify_email.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String email = reset_email.getText().toString().trim();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Email field empty",
                            Toast.LENGTH_SHORT).show();
                }

                firebase_auth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPassword.this, "Email sent",
                                            Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(ResetPassword.this,
                                            "Failed to send email", Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }
        });
    }
}
