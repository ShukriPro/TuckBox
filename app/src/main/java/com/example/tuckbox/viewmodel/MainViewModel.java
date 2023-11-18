package com.example.tuckbox.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tuckbox.data.models.User;
import com.example.tuckbox.repository.AppRepository;

import java.util.List;

// ViewModel for the main screen
public class MainViewModel extends ViewModel {

    private AppRepository repository;
    private LiveData<List<User>> users;

    public MainViewModel(AppRepository repository) {
        this.repository = repository;
        this.users = repository.getAllUsers();
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    // ... other methods as per your requirements
}