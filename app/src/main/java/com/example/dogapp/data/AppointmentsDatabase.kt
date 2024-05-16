package com.example.dogapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dogapp.model.Appointment
import com.example.dogapp.utils.Constants.NAME_BD

@Database(entities = [Appointment::class], version = 1)
abstract class AppointmentsDatabase : RoomDatabase() {
    abstract fun getAppointmentsDao(): AppointmentsDao

    companion object {
        fun getDatabase(context: Context): AppointmentsDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppointmentsDatabase::class.java,
                NAME_BD
            ).build()
        }
    }
}