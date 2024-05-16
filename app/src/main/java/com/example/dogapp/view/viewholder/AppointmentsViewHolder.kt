package com.example.dogapp.view.viewholder

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.appointments
import com.example.dogapp.databinding.ItemAppointmentBinding

class AppointmentsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val binding = ItemAppointmentBinding.bind(view)
    //val bindingHome = FragmentHomeBinding.bind(view)

    fun render(appointmentsModel: appointments) {
        binding.tvItemName.text = appointmentsModel.name
        binding.tvItemDescription.text = appointmentsModel.description
        binding.tvItemOrder.text = appointmentsModel.order
        // Agregar imagen
        itemView.setOnClickListener() {

        }
    }
}