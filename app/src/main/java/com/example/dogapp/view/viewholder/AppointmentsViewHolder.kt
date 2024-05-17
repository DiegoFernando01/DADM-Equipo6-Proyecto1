package com.example.dogapp.view.viewholder

import android.os.Bundle
import android.util.Log
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.databinding.ItemAppointmentBinding
import com.example.dogapp.model.Appointment
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class AppointmentsViewHolder(binding: ItemAppointmentBinding, navController: NavController) :
    RecyclerView.ViewHolder(binding.root) {
    val bindingItem = binding
    val navController = navController
    private val client = OkHttpClient()  // Añadir esta línea para inicializar el cliente

    fun setItemAppointment(appointments: Appointment, appointmentNumberMap: HashMap<Appointment, Int>) {
        val appointmentNumber = appointmentNumberMap[appointments]
        bindingItem.tvItemName.text = appointments.name
        bindingItem.tvItemDescription.text = appointments.symptoms
        bindingItem.tvItemOrder.text = appointmentNumber.toString()

        // Agregar imagen
        fetchDogImage(appointments.breed)

        bindingItem.CVAppointments.setOnClickListener {
            val bundle = Bundle()
            bundle.putSerializable("clave", appointments)
            navController.navigate(R.id.action_homeFragment_to_DetailsFragment, bundle)
        }
    }

    private fun fetchDogImage(breed: String) {
        val url = "https://dog.ceo/api/breed/$breed/images/random"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("AppointmentsViewHolder", "Error fetching dog image", e)
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    response.body?.let {
                        val jsonResponse = JSONObject(it.string())
                        val imageUrl = jsonResponse.getString("message")
                        bindingItem.root.post {
                            Glide.with(bindingItem.root.context)
                                .load(imageUrl)
                                .into(bindingItem.ivItemPhoto) // Cambia este ID por el correcto en tu layout
                        }
                    }
                } else {
                    Log.e("AppointmentsViewHolder", "Failed to fetch dog image: ${response.message}")
                }
            }
        })
    }
}
