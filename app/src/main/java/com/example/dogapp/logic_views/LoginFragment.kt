package com.example.dogapp.logic_views

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import com.example.dogapp.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor

class LoginFragment : Fragment() { // Fragmento login

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var ivLoginFingerprint: ImageView
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: BiometricPrompt.PromptInfo

    override fun onCreateView( // Operaciones sobre la vista durante su creación
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ivLoginFingerprint = binding.ivLoginFingerprintIcon
        ivLoginFingerprint.setOnClickListener {
            checkDeviceBiometric()
        }
    }

    private fun checkDeviceBiometric() { // Verifica la compatibilidad del dispositivo con la autenticación biométrica
        val biometricManager = BiometricManager.from(requireContext())
        when(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                createBiometricListener()
                createPromptInfo()
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Toast.makeText(requireContext(), "El dispositivo no cuenta con lector de huellas dactilar", Toast.LENGTH_LONG).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Toast.makeText(requireContext(), "El lector de huellas dactilares no se encuentra disponible", Toast.LENGTH_LONG).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Toast.makeText(requireContext(), "El dispositivo no cuenta con huellas dactilares registradas", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(requireContext(), "Error de autenticación desconocido", Toast.LENGTH_LONG).show()
        }
    }

    private fun createBiometricListener() { // Listener para autenticación biométrica
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(this, executor, object: BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Snackbar.make(ivLoginFingerprint, "Autenticación biomética exitosa", Toast.LENGTH_LONG).show()
                // Enviar a vista de Administrador de citas
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Snackbar.make(ivLoginFingerprint, "Autenticación biomética fallida", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun createPromptInfo() { // Prompt para la autenticación biométrica
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación con biometría")
            .setSubtitle("Ingrese su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()
    }

}