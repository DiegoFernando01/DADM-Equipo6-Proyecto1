package com.example.dogapp.fragments

import android.os.Bundle
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.widget.addTextChangedListener
// import androidx.room.Room
import com.example.dogapp.R
import com.example.dogapp.api.RetrofitClient
import com.example.dogapp.model.DogBreedsResponse
// import com.example.dogapp.model.Cita
// import com.example.dogapp.database.AppDatabase
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NuevaCitaActivity : AppCompatActivity() {
    private lateinit var autoCompleteTextViewRaza: AutoCompleteTextView
    private lateinit var autoCompleteTextViewSintomas: AutoCompleteTextView
    private lateinit var textInputEditTextNombreMascota: TextInputEditText
    private lateinit var textInputEditTextNombrePropietario: TextInputEditText
    private lateinit var textInputEditTextTelefono: TextInputEditText
    private lateinit var botonGuardarCita: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_nueva_cita)

        // Configuración del Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Inicializar vistas
        autoCompleteTextViewRaza = findViewById(R.id.autoCompleteTextViewRaza)
        autoCompleteTextViewSintomas = findViewById(R.id.autoCompleteTextViewSintomas)
        textInputEditTextNombreMascota = findViewById(R.id.textInputEditTextNombreMascota)
        textInputEditTextNombrePropietario = findViewById(R.id.textInputEditTextNombrePropietario)
        textInputEditTextTelefono = findViewById(R.id.textInputEditTextTelefono)
        botonGuardarCita = findViewById(R.id.botonGuardarCita)

        // Obtener lista de razas de perros usando Api-Retrofit
        obtenerListaRazasPerros()

        // Configurar lista de síntomas desde strings.xml
        val sintomas = resources.getStringArray(R.array.symptoms_array)
        val sintomasAdapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, sintomas)
        autoCompleteTextViewSintomas.setAdapter(sintomasAdapter)

        // Añadir listeners para habilitar/deshabilitar el botón
        setupTextChangeListeners()

        // Configurar listener del botón
        botonGuardarCita.setOnClickListener {
            if (autoCompleteTextViewSintomas.text.isNullOrEmpty()) {
                Toast.makeText(this, "Selecciona un síntoma", Toast.LENGTH_SHORT).show()
            } else {
                // saveAppointment()
                Toast.makeText(this, "Función de guardar cita aún no implementada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun obtenerListaRazasPerros() {
        val call: Call<DogBreedsResponse> = RetrofitClient.dogApiService.getAllBreeds()
        call.enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    val breeds = breedsResponse?.message?.keys?.toList() ?: listOf()
                    val adapter = ArrayAdapter(this@NuevaCitaActivity, android.R.layout.simple_dropdown_item_1line, breeds)
                    autoCompleteTextViewRaza.setAdapter(adapter)
                }
            }
            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                Toast.makeText(this@NuevaCitaActivity, "Error al obtener las razas de perros", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setupTextChangeListeners() {
        val campos = listOf(textInputEditTextNombreMascota, autoCompleteTextViewRaza, textInputEditTextNombrePropietario, textInputEditTextTelefono)
        for (campo in campos) {
            campo.addTextChangedListener {
                botonGuardarCita.isEnabled = camposCompletos()
            }
        }
    }

    private fun camposCompletos(): Boolean {
        val nombreMascota = textInputEditTextNombreMascota.text.toString()
        val raza = autoCompleteTextViewRaza.text.toString()
        val nombrePropietario = textInputEditTextNombrePropietario.text.toString()
        val telefono = textInputEditTextTelefono.text.toString()
        return nombreMascota.isNotEmpty() && raza.isNotEmpty() && nombrePropietario.isNotEmpty() && telefono.isNotEmpty()
    }

    /*
    private fun saveAppointment() {
        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "appointments-db"
        ).allowMainThreadQueries().build()
        val appointmentDao = db.appointmentDao()

        val cita = Cita(
            nombreMascota = textInputEditTextNombreMascota.text.toString(),
            raza = autoCompleteTextViewRaza.text.toString(),
            nombrePropietario = textInputEditTextNombrePropietario.text.toString(),
            telefono = textInputEditTextTelefono.text.toString(),
            sintomas = autoCompleteTextViewSintomas.text.toString()
        )

        appointmentDao.insert(cita)
        Toast.makeText(this, "Cita guardada", Toast.LENGTH_SHORT).show()

        // Navegar a Home Administrador de Citas
        finish()
    }
    */
}