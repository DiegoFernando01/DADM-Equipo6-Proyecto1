package com.example.dogapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Appointment(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 1,
    val name: String,
    val breed: String,
    val owner: String,
    val symptoms: String,
    val phone: String
) : Serializable