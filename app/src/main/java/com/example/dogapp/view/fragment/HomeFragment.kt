package com.example.dogapp.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentHomeBinding
import com.example.dogapp.view.MainActivity
import com.example.dogapp.view.adapter.AppointmentsAdapter
import com.example.dogapp.viewmodel.AppointmentViewModel

class HomeFragment : Fragment() { // Fragmento administrador de citas

    private lateinit var binding: FragmentHomeBinding
    private val appointmentsViewModel: AppointmentViewModel by viewModels()

    override fun onCreateView( // Operaciones sobre la vista durante su creación
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Operaciones sobre la vista después de creada
        super.onViewCreated(view, savedInstanceState)
        observerAppointmentsList()
        binding.btHomeCreateAppointment.setOnClickListener {
            findNavController().navigate(R.id.action_HomeFragment_to_CreateFragment)
        }
    }

    override fun onAttach(context: Context) { // Método de salida desde el Home
        super.onAttach(context)
        if (context is MainActivity) {
            val onBackPressedDispatcher = context.getBackPressDispatcher()
            onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    activity?.finish()
                }
            })
        }
    }

    private fun observerAppointmentsList() {
        appointmentsViewModel.getAllAppointments()
        appointmentsViewModel.listAppointment.observe(viewLifecycleOwner) { listAppointments ->
            val recycler = binding.recyclerAppointments
            val layoutManager = LinearLayoutManager(context)
            recycler.layoutManager = layoutManager
            val adapter = AppointmentsAdapter(listAppointments, findNavController())
            recycler.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }
}