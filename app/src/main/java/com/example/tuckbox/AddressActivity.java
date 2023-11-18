package com.example.tuckbox;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class AddressActivity extends AppCompatActivity {
    private EditText editTextStreetAddress, editTextCity, editTextPostal;
    private DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);


        editTextStreetAddress = findViewById(R.id.editTextStreetAddress);
        editTextCity = findViewById(R.id.editTextCity);
        editTextPostal = findViewById(R.id.editTextPostal);

        databaseReference = FirebaseDatabase.getInstance().getReference("addresses");
    }
    public void onAddAddressClick(View view) {
        String streetAddress = editTextStreetAddress.getText().toString().trim();
        String city = editTextCity.getText().toString().trim();
        String postal = editTextPostal.getText().toString().trim();

        if (!streetAddress.isEmpty() && !city.isEmpty() && !postal.isEmpty()) {
            String id = databaseReference.push().getKey();
            Map<String, String> address = new HashMap<>();
            address.put("address", streetAddress + " " + city + " " + postal);

            databaseReference.child(id).setValue(address).addOnSuccessListener(aVoid -> {
                Toast.makeText(AddressActivity.this, "Address added", Toast.LENGTH_SHORT).show();
                finish();
            });
        } else {
            Toast.makeText(AddressActivity.this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void  onCancelClick(View view)
    {
        finish();
    }
}