package com.example.tuckbox;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuckbox.data.models.User;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import androidx.annotation.NonNull;

public class SingupActivity extends AppCompatActivity {
    private EditText edtUserEmail, edtPassword, edtFirstName, edtLastName, edtMobile;
    private Button btnSignup;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        // Initializing views
        edtUserEmail = findViewById(R.id.edtUserEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtFirstName = findViewById(R.id.edtFirstName);
        edtLastName = findViewById(R.id.edtLastName);
        edtMobile = findViewById(R.id.edtMobile);
        btnSignup = findViewById(R.id.btnSignup);

        // Firebase database reference
        mDatabase = FirebaseDatabase.getInstance().getReference();

        // Set click listener for the sign up button
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signupUser();
            }
        });
    }
    public void btnCancel(View view){finish();}
    private void signupUser() {
        String email = edtUserEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();
        String firstName = edtFirstName.getText().toString().trim();
        String lastName = edtLastName.getText().toString().trim();
        String mobile = edtMobile.getText().toString().trim();

        // Checking if the fields are filled
        if (email.isEmpty() || password.isEmpty() || firstName.isEmpty() || lastName.isEmpty() || mobile.isEmpty()) {
            Toast.makeText(SingupActivity.this, "Failed to update user details", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create user object
        User user = new User();
        user.user_Email = email;
        user.password = password;
        user.first_Name = firstName;
        user.last_Name = lastName;
        user.mobile = mobile;

        mDatabase.child("users").push().setValue(user)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            // Sign up successful
                            Toast.makeText(SingupActivity.this, "Sign up successful", Toast.LENGTH_SHORT).show();
                            finish();  // This will close SignupActivity and return to LoginActivity
                        } else {
                            // Handle the error, for example, show an error message
                            Toast.makeText(SingupActivity.this, "Sign up failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}