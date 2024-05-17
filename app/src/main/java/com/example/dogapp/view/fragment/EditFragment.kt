package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.DogBreedsResponse
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentEditBinding
import com.example.dogapp.model.Appointment
import com.example.dogapp.viewmodel.AppointmentViewModel
import com.example.dogapp.webservice.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
        // Obtener lista de razas de perros usando Api-Retrofit
        obtenerListaRazasPerros()
        // Añadir listeners para habilitar/deshabilitar el botón
        setupTextChangeListeners()
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
        val appointment = Appointment(
            receivedAppointment.id,
            nombreMascota,
            raza,
            nombrePropietario,
            receivedAppointment.symptoms,
            telefono
        )
        appointmentViewModel.updateAppointment(appointment)
        findNavController().navigate(R.id.action_EditFragment_to_HomeFragment)
    }

    private fun obtenerListaRazasPerros() {
        val call: Call<DogBreedsResponse> = RetrofitClient.dogApiService.getAllBreeds()
        call.enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(
                call: Call<DogBreedsResponse>,
                response: Response<DogBreedsResponse>
            ) {
                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    val breeds = breedsResponse?.message?.keys?.toList() ?: listOf()
                    val adapter = ArrayAdapter(
                        requireContext(),
                        android.R.layout.simple_dropdown_item_1line,
                        breeds
                    )
                    binding.autoCompleteTextViewRaza.setAdapter(adapter)
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                Toast.makeText(
                    requireContext(),
                    "Error al obtener las razas de perros",
                    Toast.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun setupTextChangeListeners() {
        val campos = listOf(
            binding.textInputEditTextName,
            binding.autoCompleteTextViewRaza,
            binding.textInputEditTextPropietario,
            binding.textInputEditTextTelefono
        )
        for (campo in campos) {
            campo.addTextChangedListener {
                binding.buttonEditar.isEnabled = camposCompletos()
            }
        }
    }

    private fun camposCompletos(): Boolean {
        val nombreMascota = binding.textInputEditTextName.text.toString()
        val raza = binding.autoCompleteTextViewRaza.text.toString()
        val nombrePropietario = binding.textInputEditTextPropietario.text.toString()
        val telefono = binding.textInputEditTextTelefono.text.toString()
        return nombreMascota.isNotEmpty() && raza.isNotEmpty() && nombrePropietario.isNotEmpty() && telefono.isNotEmpty()
    }
}