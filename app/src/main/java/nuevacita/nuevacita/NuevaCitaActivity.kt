package nuevacita.nuevacita

import android.os.Bundle
import androidx.activity.addCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import nuevacita.nuevacita.R

class NuevaCitaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_appointment)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        toolbar.setNavigationIcon(R.drawable.baseline_arrow_back_ios_pink)

        // Manejar el evento de presión del botón de regreso con OnBackPressedDispatcher
        onBackPressedDispatcher.addCallback(this) {
            // Aquí puedes manejar el regreso
            // Por ejemplo: Navegar a otro fragmento o simplemente llamar a finish()
            finish()  // Esto cerrará la actividad actual
        }
    }
}

