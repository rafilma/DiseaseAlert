package com.rafil.diseasealert

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class AboutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }

    fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> {
                val backIntent = Intent(this, MenuActivity::class.java)
                startActivity(backIntent)
                finish() // Optional: Call finish() to close the current activity after starting MenuActivity
            }
        }
    }
}
