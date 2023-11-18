package com.example.tuckbox.data.models;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(foreignKeys = @ForeignKey(entity = User.class, parentColumns = "user_ID", childColumns = "usersUser_ID"))
public class DeliveryAddress {
    @PrimaryKey
    public int address_ID;
    public String address;
    public int usersUser_ID;
}