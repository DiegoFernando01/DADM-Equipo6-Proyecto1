package com.example.dogapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "citas")
data class Cita(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val nombreMascota: String,
    val raza: String,
    val nombrePropietario: String,
    val telefono: String,
    val sintomas: String
) : Serializable