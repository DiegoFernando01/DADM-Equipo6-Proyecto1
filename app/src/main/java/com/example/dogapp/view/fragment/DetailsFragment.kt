package com.example.dogapp.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentDetailsBinding
import com.example.dogapp.model.Appointment
import com.example.dogapp.viewmodel.AppointmentViewModel
import okhttp3.*
import org.json.JSONObject
import java.io.IOException


class DetailsFragment : Fragment() { // Fragmento detalle citas

    private lateinit var binding: FragmentDetailsBinding
    private val appointmentViewModel: AppointmentViewModel by viewModels()
    private lateinit var receivedAppointment: Appointment
    private val client = OkHttpClient()

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
            val bundle = Bundle().apply {
                putSerializable("dataAppointment", receivedAppointment)
            }
            findNavController().navigate(R.id.action_DetailsFragment_to_EditFragment, bundle)
        }
        binding.ibtDetailsBack.setOnClickListener() {
            findNavController().popBackStack()
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
        binding.tvDetailsBreed.text = "${receivedAppointment.breed}"
        binding.tvDetailsSymptoms.text = "${receivedAppointment.symptoms}"
        binding.tvDetailsOwner.text = "Propietario: ${receivedAppointment.owner}"
        binding.tvDetailsPhone.text = "Teléfono: ${receivedAppointment.phone}"
        // Agregar demás campos de texto
    }
    private fun fetchDogImage(breed: String) {
        val url = "https://dog.ceo/api/breed/$breed/images/random"
        Log.d("DetailsFragment", "Fetching image from URL: $url")
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("DetailsFragment", "Error fetching dog image", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.let {
                        val jsonResponse = JSONObject(it.string())
                        val imageUrl = jsonResponse.getString("message")
                        Log.d("DetailsFragment", "Fetched image URL: $imageUrl")  // Log para verificar la URL de la imagen
                        binding.root.post {
                            Glide.with(binding.root.context)
                                .load(imageUrl)
                                .into(binding.imageView)
                        }
                    }
                } else {
                    Log.e("DetailsFragment", "Failed to fetch dog image: ${response.message}")
                }
            }
        })
    }
}