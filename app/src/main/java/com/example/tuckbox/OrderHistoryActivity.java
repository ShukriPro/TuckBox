package com.example.tuckbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.tuckbox.data.models.Order;
import com.example.tuckbox.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryActivity extends AppCompatActivity {
    private String email;
    private TextView tvFullName;
    private DatabaseReference mDatabase;
    private RecyclerView recyclerView;
    private List<Order> orders = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);

        // Initialize views and database reference
        initViews();

        // Get data passed from LoginActivity
        email = getIntent().getStringExtra("email");

        // Fetch user details and set to TextView
        getUserDetails(email);

        // Fetch and display current orders
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        fetchDataFromFirebase();
    }
    private void fetchDataFromFirebase() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("orders");

        mDatabase.orderByChild("user_Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        String fullNameToMatch = user.first_Name + " " + user.last_Name;
                        tvFullName.setText(fullNameToMatch);

                        databaseReference.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                orders.clear();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    Order order = snapshot.getValue(Order.class);
                                    if (order != null && order.getFullName().equals(fullNameToMatch)) {
                                        orders.add(order);
                                    }
                                }
                                OrderAdapter adapter = new OrderAdapter(orders);
                                recyclerView.setAdapter(adapter);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                // Handle error
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetails(email);
    }

    private void initViews() {
        tvFullName = findViewById(R.id.tvFullName);
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
    }

    private void getUserDetails(String email) {
        mDatabase.orderByChild("user_Email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    User user = userSnapshot.getValue(User.class);
                    if (user != null) {
                        // Set the user's first and last name to the TextView
                        tvFullName.setText(user.first_Name + " " + user.last_Name);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
            }
        });
    }

    //Nav logic
    public void navigateToHome(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void navigateToPlaceOrder(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void navigateToUpdateInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void btnBack(View view) {
        finish();
    }

}