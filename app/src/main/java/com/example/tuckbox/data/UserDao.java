package com.example.tuckbox.data;
import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.tuckbox.data.models.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM User")
    LiveData<List<User>> getAllUsers();

    @Insert
    void insertAll(List<User> firebaseUsers);
}
