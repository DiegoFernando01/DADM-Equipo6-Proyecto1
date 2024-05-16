package com.example.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.R
import com.example.dogapp.appointments
import com.example.dogapp.view.viewholder.AppointmentsViewHolder

class AppointmentsAdapter(private val appointmentsList: List<appointments>) :
    RecyclerView.Adapter<AppointmentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppointmentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return AppointmentsViewHolder(
            layoutInflater.inflate(
                R.layout.item_appointment,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = appointmentsList.size

    override fun onBindViewHolder(holder: AppointmentsViewHolder, position: Int) {
        val item = appointmentsList[position]
        holder.render(item)
    }

}