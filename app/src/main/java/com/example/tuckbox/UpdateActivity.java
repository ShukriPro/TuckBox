package com.example.tuckbox;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tuckbox.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
public class UpdateActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;

    // Declare your EditText fields
    private EditText edtUpdateEmail, edtUpdatePassword, edtUpdateFirstName, edtUpdateLastName, edtUpdateMobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        // Initialize your EditText fields
        edtUpdateEmail = findViewById(R.id.edtUpdateEmail);
        edtUpdatePassword = findViewById(R.id.edtUpdatePassword);
        edtUpdateFirstName = findViewById(R.id.edtUpdateFirstName);
        edtUpdateLastName = findViewById(R.id.edtUpdateLastName);
        edtUpdateMobile = findViewById(R.id.edtUpdateMobile);

        mDatabase = FirebaseDatabase.getInstance().getReference("users");

        // Retrieve email from the intent
        String userEmail = getIntent().getStringExtra("userEmail");

        // Use the email to query Firebase and get user details
        getUserDetails(userEmail);
    }
    private void getUserDetails(String email) {
        mDatabase.orderByChild("user_Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        // Populate the EditText fields with the retrieved user details
                        edtUpdateEmail.setText(user.user_Email);
                        edtUpdatePassword.setText(user.password);
                        edtUpdateFirstName.setText(user.first_Name);
                        edtUpdateLastName.setText(user.last_Name);
                        edtUpdateMobile.setText(user.mobile);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    public void btnDeleteUser(View view){
        new AlertDialog.Builder(this)
                .setTitle("Delete Account")
                .setMessage("Are you sure you want to delete your account?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                        String email = edtUpdateEmail.getText().toString().trim();
                        mDatabase.orderByChild("user_Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                                    userSnapshot.getRef().removeValue();
                                }
                                Toast.makeText(UpdateActivity.this, "Account Deleted", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateActivity.this, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                                finish();

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                Toast.makeText(UpdateActivity.this, "Failed to delete account", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                })
                .setNegativeButton("No", null)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }
    public void btnUpdate(View view){
        String email = edtUpdateEmail.getText().toString().trim();
        String password = edtUpdatePassword.getText().toString().trim();
        String firstName = edtUpdateFirstName.getText().toString().trim();
        String lastName = edtUpdateLastName.getText().toString().trim();
        String mobile = edtUpdateMobile.getText().toString().trim();

        // Create a User object with the updated information
        User updatedUser = new User();
        updatedUser.user_Email = email;
        updatedUser.password = password;
        updatedUser.first_Name = firstName;
        updatedUser.last_Name = lastName;
        updatedUser.mobile = mobile;

        // Update the user in the Firebase database
        mDatabase.orderByChild("user_Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    // Update user details in the database
                    userSnapshot.getRef().setValue(updatedUser);
                }
                Toast.makeText(UpdateActivity.this, "User details updated successfully", Toast.LENGTH_SHORT).show();
                finish(); // Close the UpdateActivity and return to the previous activity
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(UpdateActivity.this, "Failed to update user details", Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void btnCancel(View view){
        finish();
    }
    //Nav logic
    public void navigateToPlaceOrder(View view) {
        Intent intent = new Intent(this, OrderActivity.class);
        startActivity(intent);
    }

    public void navigateToUpdateInfo(View view) {
        Intent intent = new Intent(this, UpdateActivity.class);
        startActivity(intent);
    }

    public void navigateToOrderHistory(View view) {
        Intent intent = new Intent(this, OrderHistoryActivity.class);
        startActivity(intent);
    }
}

