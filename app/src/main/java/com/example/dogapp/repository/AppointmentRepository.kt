package com.example.dogapp.repository

import android.content.Context
import com.example.dogapp.data.AppointmentsDao
import com.example.dogapp.data.AppointmentsDatabase
import com.example.dogapp.model.Appointment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppointmentRepository(val context: Context) {
    private var appointmentsDao: AppointmentsDao =
        AppointmentsDatabase.getDatabase(context).getAppointmentsDao()

    suspend fun insertAppointment(appointment: Appointment) {
        withContext(Dispatchers.IO) {
            appointmentsDao.insertAppointment(appointment)
        }
    }

    suspend fun getAllAppointments(): MutableList<Appointment> {
        return withContext(Dispatchers.IO) {
            appointmentsDao.getAllAppointments()
        }
    }

    suspend fun deleteAppointments(appointment: Appointment) {
        withContext(Dispatchers.IO) {
            appointmentsDao.deleteAppointment(appointment)
        }
    }

    suspend fun updateAppointments(appointment: Appointment) {
        return withContext(Dispatchers.IO) {
            appointmentsDao.updateAppointment(appointment)
        }
    }
}