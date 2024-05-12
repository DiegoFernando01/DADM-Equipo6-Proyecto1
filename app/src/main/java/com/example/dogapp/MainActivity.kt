package com.example.dogapp

import android.os.Bundle
import android.util.Log
import android.view.View
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
import java.util.Objects
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
            checkDeviceBiometric(it)
        }

    }

    private fun createPromptInfo() {
        promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Autenticación con biometría")
            .setSubtitle("Ingrese su huella digital")
            .setNegativeButtonText("CANCEL")
            .build()
    }
    private fun checkDeviceBiometric(v: View) {
        val biometricManager = BiometricManager.from(this)
        var fingerPrintReader = false; var message: String
        when(biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
            BiometricManager.BIOMETRIC_SUCCESS -> {
                fingerPrintReader = true
                message = "La aplicación puede autenticar con biometría dactilar"
                createBiometricListener()
                createPromptInfo()
                biometricPrompt.authenticate(promptInfo)
            }
            BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> {
                message = "El dispositivo no cuenta con lector de huellas dactilar"
            }
            BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> {
                message = "El lector de huellas dactilares no se encuentra disponible"
            }
            BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> {
                message = "No fue posible habilitar el lector de huellas dactilares"
            }
            else -> {
                val biometricState = when (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)) {
                    BiometricManager.BIOMETRIC_SUCCESS -> "BIOMETRIC_SUCCESS"
                    BiometricManager.BIOMETRIC_ERROR_NO_HARDWARE -> "BIOMETRIC_ERROR_NO_HARDWARE"
                    BiometricManager.BIOMETRIC_ERROR_HW_UNAVAILABLE -> "BIOMETRIC_ERROR_HW_UNAVAILABLE"
                    BiometricManager.BIOMETRIC_ERROR_NONE_ENROLLED -> "BIOMETRIC_ERROR_NONE_ENROLLED"
                    else -> "Unknown"
                }
                message = "Error desconocido: $biometricState"
                Log.e("Lector de huellas", message)
                val biometricResult = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL)
                Log.e("Lector de huellas", "Resultado de la autenticación biométrica: $biometricResult")
            }
        }
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun createBiometricListener() {
        executor = ContextCompat.getMainExecutor(this)
        biometricPrompt = BiometricPrompt(this, executor, object: BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Snackbar.make(ivLoginFingerprint, "Autenticación biomética exitosa", Toast.LENGTH_LONG).show()
            }
            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Snackbar.make(ivLoginFingerprint, "Autenticación biomética fallida", Toast.LENGTH_LONG).show()
            }
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Snackbar.make(ivLoginFingerprint, errString, Toast.LENGTH_LONG).show()
            }
        })
    }
}