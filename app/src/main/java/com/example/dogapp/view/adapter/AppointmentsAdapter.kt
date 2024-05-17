package com.example.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.databinding.ItemAppointmentBinding
import com.example.dogapp.model.Appointment
import com.example.dogapp.view.viewholder.AppointmentsViewHolder

class AppointmentsAdapter(private val appointmentsList: MutableList<Appointment>, private val navController: NavController) :
    RecyclerView.Adapter<AppointmentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentsViewHolder {
        val binding = ItemAppointmentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppointmentsViewHolder(binding, navController)
    }

    override fun getItemCount(): Int = appointmentsList.size

    override fun onBindViewHolder(holder: AppointmentsViewHolder, position: Int) {
        val appointment = appointmentsList[position]
        holder.setItemAppointment(appointment)
    }

}