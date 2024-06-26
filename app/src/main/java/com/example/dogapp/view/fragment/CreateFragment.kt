package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.dogapp.DogBreedsResponse
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentCreateBinding
import com.example.dogapp.model.Appointment
import com.example.dogapp.viewmodel.AppointmentViewModel
import com.example.dogapp.webservice.RetrofitClient
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateFragment : Fragment() {
    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!
    private lateinit var autoCompleteTextViewRaza: AutoCompleteTextView
    private lateinit var autoCompleteTextViewSintomas: AutoCompleteTextView
    private lateinit var textInputEditTextNombreMascota: TextInputEditText
    private lateinit var textInputEditTextNombrePropietario: TextInputEditText
    private lateinit var textInputEditTextTelefono: TextInputEditText
    private lateinit var botonGuardarCita: MaterialButton
    private val appointmentViewModel: AppointmentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        controllers()

        // Inicializar vistas
        autoCompleteTextViewRaza = binding.autoCompleteTextViewRaza
        autoCompleteTextViewSintomas = binding.autoCompleteTextViewSintomas
        textInputEditTextNombreMascota = binding.textInputEditTextNombreMascota
        textInputEditTextNombrePropietario = binding.textInputEditTextNombrePropietario
        textInputEditTextTelefono = binding.textInputEditTextTelefono
        botonGuardarCita = binding.botonGuardarCita

        // Obtener lista de razas de perros usando Api-Retrofit
        obtenerListaRazasPerros()

        // Configurar lista de síntomas desde strings.xml
        val sintomas = resources.getStringArray(R.array.symptoms_array)
        val sintomasAdapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, sintomas)
        autoCompleteTextViewSintomas.setAdapter(sintomasAdapter)

        // Añadir listeners para habilitar/deshabilitar el botón
        setupTextChangeListeners()

        // Configurar listener del botón
        botonGuardarCita.setOnClickListener {
            if (autoCompleteTextViewSintomas.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
            } else {
                saveAppointment()
                Toast.makeText(
                    requireContext(),
                    "Cita agendada correctamente.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onDestroyView() { // Operaciones sobre la vista durante su destrucción
        super.onDestroyView()
        _binding = null
    }

    private fun controllers() {
        binding.ibtCreateBack.setOnClickListener() {
            findNavController().popBackStack()
        }
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
                    autoCompleteTextViewRaza.setAdapter(adapter)
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
            textInputEditTextNombreMascota,
            autoCompleteTextViewRaza,
            textInputEditTextNombrePropietario,
            textInputEditTextTelefono
        )
        for (campo in campos) {
            campo.addTextChangedListener {
                botonGuardarCita.isEnabled = camposCompletos()
            }
        }
    }

    private fun camposCompletos(): Boolean {
        val nombreMascota = textInputEditTextNombreMascota.text.toString()
        val raza = autoCompleteTextViewRaza.text.toString()
        val nombrePropietario = textInputEditTextNombrePropietario.text.toString()
        val telefono = textInputEditTextTelefono.text.toString()
        return nombreMascota.isNotEmpty() && raza.isNotEmpty() && nombrePropietario.isNotEmpty() && telefono.isNotEmpty()
    }

    private fun saveAppointment() {

        val nombreMascota = textInputEditTextNombreMascota.text.toString()
        val raza = autoCompleteTextViewRaza.text.toString()
        val nombrePropietario = textInputEditTextNombrePropietario.text.toString()
        val telefono = textInputEditTextTelefono.text.toString()
        val sintomas = autoCompleteTextViewSintomas.text.toString()

        appointmentViewModel.insertAppointment(
            Appointment(
                name = nombreMascota,
                breed = raza,
                owner = nombrePropietario,
                symptoms = sintomas,
                phone = telefono
            )
        )
        Toast.makeText(requireContext(), "Cita guardada", Toast.LENGTH_SHORT).show()

        // Navegar a Home Administrador de Citas
        findNavController().popBackStack()
    }
}