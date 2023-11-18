package com.example.tuckbox.repository;

import androidx.lifecycle.LiveData;

import com.example.tuckbox.data.UserDao;
import com.example.tuckbox.data.models.User;
import com.example.tuckbox.datasource.FirebaseSource;
import com.google.android.gms.tasks.OnSuccessListener;


import java.util.List;

// Class that communicates with the data sources
public class AppRepository {

    private UserDao userDao;
    private FirebaseSource firebaseSource;

    public AppRepository(UserDao userDao, FirebaseSource firebaseSource) {
        this.userDao = userDao;
        this.firebaseSource = firebaseSource;
    }

    public LiveData<List<User>> getAllUsers() {
        return userDao.getAllUsers();
    }

    public void syncWithFirebase() {
        firebaseSource.fetchUsers(new OnSuccessListener<List<User>>() {
            @Override
            public void onSuccess(List<User> firebaseUsers) {
                // Convert firebaseUsers to a format suitable for your ROOM database and save it.
                userDao.insertAll(firebaseUsers);
            }
        });
    }

    // ... other methods to interact with the data source
}
