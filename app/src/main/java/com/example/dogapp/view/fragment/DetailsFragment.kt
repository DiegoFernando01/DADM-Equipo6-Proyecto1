package com.example.dogapp.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.dogapp.R
import com.example.dogapp.databinding.FragmentDetailsBinding
import com.example.dogapp.data.AppDatabase
import com.example.dogapp.model.Cita

class DetailsFragment : Fragment() { // Fragmento detalle citas

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val citaId = args.citaId
        val db = Room.databaseBuilder(
            requireContext(),
            AppDatabase::class.java, "appointments-db"
        ).allowMainThreadQueries().build()
        val cita = db.citaDao().getCitaById(citaId)

        // Configurar la toolbar
        val toolbar = binding.toolbar
        toolbar.title = cita.nombreMascota
        toolbar.setNavigationIcon(R.drawable.details_icon_back)
        toolbar.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_DetailsFragment_to_HomeFragment)
        }

        // Mostrar la imagen de la mascota
        Glide.with(requireContext())
            .load(cita.imageUrl)
            .into(binding.ivPhoto)

        // Mostrar detalles de la cita
        binding.tvRaza.text = cita.raza
        binding.tvSintomas.text = cita.sintomas
        binding.tvPropietario.text = getString(R.string.propietario_label, cita.nombrePropietario)
        binding.tvTelefono.text = getString(R.string.telefono_label, cita.telefono)
        binding.tvTurno.text = getString(R.string.turno_label, cita.id)

        // Configurar el botón de eliminar
        binding.buttonDelete.setOnClickListener {
            lifecycleScope.launch {
                db.citaDao().deleteCita(cita)
                Toast.makeText(requireContext(), "Cita eliminada", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_DetailsFragment_to_HomeFragment)
            }
        }

        // Configurar el botón de editar
        binding.buttonEditar.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsFragmentToEditFragment(citaId)
            findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}