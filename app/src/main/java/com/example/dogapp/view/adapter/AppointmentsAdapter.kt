package com.example.dogapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dogapp.appointments
import com.example.dogapp.databinding.ItemAppointmentBinding
import com.example.dogapp.webservice.RetrofitClient
import com.example.dogapp.ImageResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppointmentsAdapter(private val appointmentsList: List<appointments>) :
    RecyclerView.Adapter<AppointmentsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemAppointmentBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(appointment: appointments) {
            binding.tvName.text = appointment.name
            binding.tvDescription.text = appointment.description
            binding.tvOrder.text = appointment.order

            RetrofitClient.dogApiService.getBreedImage(appointment.raza.lowercase())
                .enqueue(object : Callback<ImageResponse> {
                override fun onResponse(
                    call: Call<ImageResponse>,
                response: Response<ImageResponse>
                ) {
                if (response.isSuccessful) {
                    val imageUrl = response.body()?.message
                    Glide.with(binding.root.context)
                        .load(imageUrl)
                        .circleCrop()
                        .into(binding.ivPhoto)
                }
            }

                override fun onFailure(call: Call<ImageResponse>, t: Throwable) {
                // Handle failure
            }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemAppointmentBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(appointmentsList[position])
    }

    override fun getItemCount(): Int = appointmentsList.size

}
