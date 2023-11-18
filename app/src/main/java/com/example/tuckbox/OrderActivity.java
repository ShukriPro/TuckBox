package com.example.tuckbox;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.tuckbox.data.models.Food;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import android.content.Intent;
import android.widget.Toast;

import java.util.Map;
import java.util.HashMap;

public class OrderActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FoodAdapter foodAdapter;
    private List<Food> foodList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        foodAdapter = new FoodAdapter(foodList);
        recyclerView.setAdapter(foodAdapter);

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("foods");
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                foodList.clear();
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Food food = postSnapshot.getValue(Food.class);

                    // Assuming that your Food class has setters for these fields
                    food.setExtra_choice_type(postSnapshot.child("extra_choice_type").getValue(String.class));

                    List<String> extraChoiceOptions = new ArrayList<>();
                    for (DataSnapshot optionSnapshot : postSnapshot.child("extra_choice_options").getChildren()) {
                        extraChoiceOptions.add(optionSnapshot.getValue(String.class));
                    }
                    food.setExtra_choice_options(extraChoiceOptions);

                    foodList.add(food);
                }
                foodAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });

        // Retrieve the data passed from MainActivity
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        String fullName = intent.getStringExtra("fullName");

        // Set the fullName to the TextView
        TextView tvFullName = findViewById(R.id.tvFullName);
        tvFullName.setText(fullName);

        // Other initialization code
        initializeTimeSlotSpinner();
        initializeCitiesSpinner();
        initializeAddressSpinner();

    }

    private void initializeTimeSlotSpinner(){
        //spinnerTimeSlot
        String[] timeSlot = {"11:45 - 12:15","12:15 - 12:45","12:45 - 13:15","13:15 - 13:45"};
        ArrayAdapter<String> timeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, timeSlot);
        timeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner timeSpinner = findViewById(R.id.spinnerTimeSlot);
        timeSpinner.setAdapter(timeAdapter);
    }
    private void initializeCitiesSpinner(){
        //Cities spinner
        String[] cities = {"Palmerstron North", "Fielding", "Ashurst", "Longburn"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, cities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = findViewById(R.id.spinnerCity);
        spinner.setAdapter(adapter);
    }
    private void initializeAddressSpinner() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("addresses");
        List<String> addressList = new ArrayList<>();
        ArrayAdapter<String> addressAdapter;

        Spinner spinnerAddress = findViewById(R.id.spinnerAddress);

        addressAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, addressList);
        addressAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAddress.setAdapter(addressAdapter);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                addressList.clear();
                for (DataSnapshot addressSnapshot : dataSnapshot.getChildren()) {
                    String address = addressSnapshot.child("address").getValue(String.class);
                    addressList.add(address);
                }
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }

    public void btnPlaceOrder(View view) {
        DatabaseReference orderDatabase = FirebaseDatabase.getInstance().getReference("orders");

        Map<String, Object> orderDetails = new HashMap<>();
        Spinner spinnerCity = findViewById(R.id.spinnerCity);
        Spinner spinnerTimeSlot = findViewById(R.id.spinnerTimeSlot);
        Spinner spinnerAddress = findViewById(R.id.spinnerAddress);
        TextView tvFullName = findViewById(R.id.tvFullName);

        orderDetails.put("city", spinnerCity.getSelectedItem().toString());
        orderDetails.put("timeSlot", spinnerTimeSlot.getSelectedItem().toString());
        orderDetails.put("address", spinnerAddress.getSelectedItem().toString());
        orderDetails.put("fullName", tvFullName.getText().toString());
        orderDetails.put("timestamp", System.currentTimeMillis()); // Add timestamp here

        List<Map<String, Object>> orderFoods = new ArrayList<>();
        for (Food food : foodList) {
            if (food.getQuantity() > 0) {
                Map<String, Object> foodData = new HashMap<>();
                foodData.put("foodName", food.getFoodName());
                foodData.put("quantity", food.getQuantity());
                orderFoods.add(foodData);
            }
        }

        if (!orderFoods.isEmpty()) {
            orderDetails.put("foods", orderFoods);

            // Push order details to database
            orderDatabase.push().setValue(orderDetails).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Order placed successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // Handle failure
                }
            });
        } else {
            Toast.makeText(getApplicationContext(), "Please add at least one item to your order", Toast.LENGTH_SHORT).show();
        }
    }

    public void btnCancelOrder(View view){
        finish();
    }
    public void btnAddAddress(View view){
        Intent intent = new Intent(this, AddressActivity.class);
        startActivity(intent);
    }
    //Nav logic
    public void navigateToPlaceOrder(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void navigateToUpdateInfo(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void navigateToOrderHistory(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
