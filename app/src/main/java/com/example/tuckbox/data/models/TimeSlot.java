package com.example.tuckbox.data.models;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
@Entity
public class TimeSlot {
    @PrimaryKey
    public int time_Slot_ID;
    public String time_Slot;
}
