package com.example.dogapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dogapp.model.Cita

@Dao
interface CitaDao {

    @Insert
    fun insert(cita: Cita)

    @Query("SELECT * FROM Appointment")
    fun getAllCitas(): List<Cita>

    @Delete
    suspend fun deleteAppointment(appointment: Cita)

    @Update
    suspend fun updateAppointment(appointment: Cita)
}