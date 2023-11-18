package com.example.tuckbox.datasource;


import com.example.tuckbox.data.models.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class FirebaseSource {

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public FirebaseSource() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference(); // or specify a child node if needed
    }

    public void fetchUsers(OnSuccessListener<List<User>> listOnSuccessListener) {
    }

    // Add methods to fetch data, update data, delete data, etc.
}
