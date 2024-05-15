package com.example.dogapp


import android.os.Bundle
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }

    fun getBackPressDispatcher(): OnBackPressedDispatcher {
        return onBackPressedDispatcher
    }
}