package com.example.tuckbox.data.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey
    public int user_ID;
    public String user_Email;
    public String password;
    public String first_Name;
    public String last_Name;
    public String mobile;
}