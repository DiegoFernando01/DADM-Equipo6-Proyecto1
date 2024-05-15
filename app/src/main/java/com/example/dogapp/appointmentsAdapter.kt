package com.example.dogapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class appointmentsAdapter(private val appointmentsList:List<appointments>) : RecyclerView.Adapter<appointmentsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): appointmentsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return appointmentsViewHolder(layoutInflater.inflate(R.layout.item_appointment, parent, false))
    }

    override fun getItemCount(): Int = appointmentsList.size

    override fun onBindViewHolder(holder: appointmentsViewHolder, position: Int) {
        val item = appointmentsList[position]
        holder.render(item)
    }

}