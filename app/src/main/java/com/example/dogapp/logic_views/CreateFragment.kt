package com.example.dogapp.logic_views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dogapp.databinding.FragmentCreateBinding

class CreateFragment : Fragment() { // Fragmento crear cita

    private var _binding: FragmentCreateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView( // Operaciones sobre la vista durante su creación
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // Operaciones sobre la vista después de creada
        super.onViewCreated(view, savedInstanceState)

    }

    override fun onDestroyView() { // Operaciones sobre la vista durante su destrucción
        super.onDestroyView()
        _binding = null
    }
}