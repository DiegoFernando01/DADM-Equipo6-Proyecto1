package com.example.dogapp.logic_views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.dogapp.databinding.FragmentDetailsBinding

class DetailsFragment : Fragment() { // Fragmento detalle citas

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView( // Operaciones sobre la vista durante su creación
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
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