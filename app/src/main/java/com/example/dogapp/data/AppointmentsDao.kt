package com.example.dogapp.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.dogapp.model.Appointment

@Dao
interface AppointmentsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAppointment(appointment: Appointment)

    @Query("SELECT * FROM Appointment")
    suspend fun getAllAppointments(): MutableList<Appointment>

    @Delete
    suspend fun deleteAppointment(appointment: Appointment)

    @Update
    suspend fun updateAppointment(appointment: Appointment)
}