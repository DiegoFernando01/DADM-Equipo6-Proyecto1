
package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentEditBinding
import com.example.dogapp.model.Appointment
import com.example.dogapp.viewmodel.AppointmentViewModel

class EditFragment : Fragment() { // Fragmento editar cita

    private lateinit var binding: FragmentEditBinding
    private val appointmentViewModel: AppointmentViewModel by viewModels()
    private lateinit var receivedAppointment: Appointment

    override fun onCreateView( // Operaciones sobre la vista durante su creación
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataAppointment()
        controller()
    }

    private fun controller() {
        binding.buttonEditar.setOnClickListener() {
            updateAppointment()
        }
    }

    private fun dataAppointment() {
        val receivedBundle = arguments
        receivedAppointment = receivedBundle?.getSerializable("dataAppointment") as Appointment
        binding.textInputEditTextName.setText(receivedAppointment.name)
        binding.textInputEditTextPropietario.setText(receivedAppointment.owner)
        binding.textInputEditTextTelefono.setText(receivedAppointment.phone)
        binding.autoCompleteTextViewRaza.setText(receivedAppointment.breed)
    }

    private fun updateAppointment() {
        val nombreMascota = binding.textInputEditTextName.text.toString()
        val raza = binding.autoCompleteTextViewRaza.text.toString()
        val nombrePropietario = binding.textInputEditTextPropietario.text.toString()
        val telefono = binding.textInputEditTextTelefono.text.toString()
        val appointment = Appointment(receivedAppointment.id, nombreMascota, raza, nombrePropietario, receivedAppointment.symptoms, telefono )
        appointmentViewModel.updateAppointment(appointment)
        findNavController().navigate(R.id.action_EditFragment_to_HomeFragment)
    }
}