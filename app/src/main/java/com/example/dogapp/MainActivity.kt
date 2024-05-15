package com.example.dogapp

import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.dogapp.logic_views.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() { // Función que permite salir desde el HomeFragment
        val fragment = supportFragmentManager.findFragmentById(R.id.homeFragment)
        if (fragment is HomeFragment) {
            moveTaskToBack(true)
        } else {
            super.onBackPressed()
        }
    }

    fun getBackPressDispatcher(): OnBackPressedDispatcher {
        return onBackPressedDispatcher
    }
}