package com.example.dogapp.view.viewholder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.dogapp.R
import com.example.dogapp.databinding.ItemAppointmentBinding
import com.example.dogapp.model.Appointment

class AppointmentsViewHolder(binding: ItemAppointmentBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController
    //val bindingHome = FragmentHomeBinding.bind(view)

    fun setItemAppointment(appointments: Appointment) {
        bindingItem.tvItemName.text = appointments.name
        bindingItem.tvItemDescription.text = appointments.symptoms
        bindingItem.tvItemOrder.text = appointments.id.toString()
        //Agregar aquí elementos restantes
        // Agregar imagen


        bindingItem.CVAppointments.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", appointments)
            navController.navigate(R.id.action_homeFragment_to_DetailsFragment, bundle)
        }
    }
}