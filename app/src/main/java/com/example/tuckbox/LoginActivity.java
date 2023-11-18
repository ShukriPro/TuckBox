package com.example.tuckbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tuckbox.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;  // <-- Added this line

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername, edtPassword;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    public void btnSignUp(View view) {
        Intent page = new Intent(this, SingupActivity.class);
        startActivity(page);
    }

    public void btnLogin(View view) {
        String email = edtUsername.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            // Handle empty fields, e.g., show a toast message
            Toast.makeText(getApplicationContext(), "Email and Password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        mDatabase.orderByChild("user_Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User loggedUser = null;

                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null && user.password.equals(password)) {
                        loggedUser = user;
                        break;
                    }
                }

                if (loggedUser != null) {
                    // Email and password match, navigate to MainActivity
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("email", loggedUser.user_Email);   // Passing the email
                    intent.putExtra("firstName", loggedUser.first_Name);
                    intent.putExtra("lastName", loggedUser.last_Name);
                    startActivity(intent);
                    finish();  // Optionally, to remove LoginActivity from the stack
                    Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT).show();
                } else {
                    // Handle login failure, e.g., show a toast message
                    Toast.makeText(getApplicationContext(), "Email and Password do not exist", Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });

    }
}
