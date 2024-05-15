package com.example.dogapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.databinding.ItemAppointmentBinding

class appointmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemAppointmentBinding.bind(view)

    fun render(appointmentsModel: appointments) {
        binding.tvItemName.text = appointmentsModel.name
        binding.tvItemDescription.text = appointmentsModel.description
        binding.tvItemOrder.text = appointmentsModel.order
    }
}