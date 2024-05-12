package com.example.dogapp


import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.biometric.BiometricPrompt.PromptInfo
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import com.example.dogapp.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var ivLoginFingerprint: ImageView
    private lateinit var executor: Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var promptInfo: PromptInfo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        ivLoginFingerprint = binding.ivLoginFingerprintIcon
        ivLoginFingerprint.setOnClickListener {
            checkDeviceBiometric()
        }
    }

    private fun createPromptInfo() { // Prompt para la autenticación biométrica
        promptInfo = PromptInfo.Builder()
            .setTitle("Autenticación con biometría")
            .setSubtitle("Ingrese su huella digital")
            .setNegativeButtonText("Cancelar")
            .build()
    }

    private fun checkDeviceBiometric() { // Verifica la compatibilidad del dispositivo con la autenticación biométrica
        val biometricManager = BiometricManager.from(this)
        when(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                createBiometricListener()
                createPromptInfo()
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> Toast.makeText(this, "El dispositivo no cuenta con lector de huellas dactilar", Toast.LENGTH_LONG).show()
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> Toast.makeText(this, "El lector de huellas dactilares no se encuentra disponible", Toast.LENGTH_LONG).show()
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> Toast.makeText(this, "El dispositivo no cuenta con huellas dactilares registradas", Toast.LENGTH_LONG).show()
            else -> Toast.makeText(this, "Error de autenticación desconocido", Toast.LENGTH_LONG).show()
        }
    }

    private fun createBiometricListener() { // Listener para autenticación biométrica
        executor = ContextCompat.getMainExecutor(this)
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
}