package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentDetailsBinding
import com.example.dogapp.model.Appointment
import com.example.dogapp.viewmodel.AppointmentViewModel

class DetailsFragment : Fragment() { // Fragmento detalle citas

    private lateinit var binding: FragmentDetailsBinding
    private val appointmentViewModel: AppointmentViewModel by viewModels()
    private lateinit var receivedAppointment: Appointment

    override fun onCreateView( // Operaciones sobre la vista durante su creación
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) { // Operaciones sobre la vista después de creada
        super.onViewCreated(view, savedInstanceState)
        dataAppointment()
        controllers()
    }

    private fun controllers() { // Eventos de la vista
        binding.buttonDelete.setOnClickListener() {
            deleteAppointment()
        }
        binding.buttonEditar.setOnClickListener() {
            val bundle = Bundle()
            bundle.putSerializable("dataAppointment", receivedAppointment)
            findNavController().navigate(R.id.action_DetailsFragment_to_EditFragment)
        }
    }

    private fun deleteAppointment() { // Eliminar en BD cita
        appointmentViewModel.deleteAppointment(receivedAppointment)
        appointmentViewModel.getAllAppointments()
        findNavController().popBackStack()
        // Mensaje de registro eliminado con éxito
    }

    private fun dataAppointment() { // Editar en BD cita
        val receivedBundle = arguments
        receivedAppointment = receivedBundle?.getSerializable("clave") as Appointment
        binding.tvDetailsPetName.text = "${receivedAppointment.name}"
        // Agregar demás campos de texto
    }
}