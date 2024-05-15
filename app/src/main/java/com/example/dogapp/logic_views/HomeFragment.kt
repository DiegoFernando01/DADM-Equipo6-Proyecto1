package com.example.dogapp.logic_views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dogapp.MainActivity
import com.example.dogapp.R
import com.example.dogapp.appointments
import com.example.dogapp.appointmentsAdapter
import com.example.dogapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() { // Fragmento administrador de citas

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val appointmentsList = listOf(
        appointments("https://static.fundacion-affinity.org/cdn/farfuture/PVbbIC-0M9y4fPbbCsdvAD8bcjjtbFc0NSP3lRwlWcE/mtime:1643275542/sites/default/files/los-10-sonidos-principales-del-perro.jpg", "Cory", "Fractura extremidad", "1"),
        appointments("https://as01.epimg.net/diarioas/imagenes/2022/05/29/actualidad/1653826510_995351_1653826595_noticia_normal_recorte1.jpg", "Zeus", "Solo duerme", "2"),
        appointments("https://media.lacapital.com.ar/p/fd083d7278a0d614ffee12938d114f25/adjuntos/203/imagenes/006/521/0006521507/642x0/smart/06-28-pabst-apjpg.jpg", "Rocky", "No come", "3")
    )

    override fun onCreateView( // Operaciones sobre la vista durante su creación
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Operaciones sobre la vista después de creada
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
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

    override fun onDestroyView() { // Operaciones sobre la vista durante su destrucción
        super.onDestroyView()
        _binding = null
    }

    private fun initRecyclerView() { // Crea los items para el recycler view
        val recyclerView = binding.recyclerAppointments
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = appointmentsAdapter(appointmentsList)
    }
}