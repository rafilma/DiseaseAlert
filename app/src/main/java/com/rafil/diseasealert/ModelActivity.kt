package com.rafil.diseasealert

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class ModelActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_model)
    }
    fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.btnBack -> run {
                    val backIntent = Intent(this@ModelActivity, MenuActivity::class.java)
                    startActivity(backIntent)
                }
            }
        }
    }
}