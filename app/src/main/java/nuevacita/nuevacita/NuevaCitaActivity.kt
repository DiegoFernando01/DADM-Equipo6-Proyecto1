package nuevacita.nuevacita

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.AutoCompleteTextView
import android.widget.ArrayAdapter
import com.example.dogapp.R
import com.example.dogapp.api.RetrofitClient
import com.example.dogapp.model.DogBreedsResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



class NuevaCitaActivity : AppCompatActivity() {
    private lateinit var autoCompleteTextViewBreed: AutoCompleteTextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nueva_cita)

        //Esto es para el toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Configurar el botón de regreso
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }

        // Inicializar AutoCompleteTextView
        autoCompleteTextViewBreed = findViewById(R.id.autoCompleteTextViewBreed)

        // Obtener la lista de razas de perros utilizando Retrofit
        obtenerListaRazasPerros()
    }

    private fun obtenerListaRazasPerros() {
        val call: Call<DogBreedsResponse> = RetrofitClient.dogApiService.getAllBreeds()

        call.enqueue(object : Callback<DogBreedsResponse> {
            override fun onResponse(call: Call<DogBreedsResponse>, response: Response<DogBreedsResponse>) {
                if (response.isSuccessful) {
                    val breedsResponse = response.body()
                    val breeds = breedsResponse?.message?.keys?.toList() ?: listOf()

                    // Configurar el adaptador para AutoCompleteTextView
                    val adapter = ArrayAdapter(this@NuevaCitaActivity, android.R.layout.simple_dropdown_item_1line, breeds)
                    autoCompleteTextViewBreed.setAdapter(adapter)
                }
            }

            override fun onFailure(call: Call<DogBreedsResponse>, t: Throwable) {
                // Manejar el fallo de la solicitud
            }
        })
    }
}



