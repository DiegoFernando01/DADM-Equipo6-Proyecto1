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

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(cita: Cita)



    @Query("SELECT * FROM citas WHERE id = :id")
    fun getCitaById(id: Int): Cita

    @Delete
    suspend fun deleteCita(cita: Cita)

    @Update
    suspend fun updateCita(cita: Cita)
}