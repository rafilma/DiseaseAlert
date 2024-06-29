package com.rafil.diseasealert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import androidx.cardview.widget.CardView
import androidx.appcompat.widget.AppCompatImageButton

class MenuActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var back: AppCompatImageButton // Use AppCompatImageButton instead of Button for back button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        val btnAbout = findViewById<ImageButton>(R.id.btnAbout) // Use AppCompatImageButton
        btnAbout.setOnClickListener {
            val intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }

        val btnDataset = findViewById<CardView>(R.id.btnDataset) // Use CardView
        btnDataset.setOnClickListener {
            val intent = Intent(this, DatasetActivity::class.java)
            startActivity(intent)
        }

        val btnFeature = findViewById<CardView>(R.id.btnFeature) // Use CardView
        btnFeature.setOnClickListener {
            val intent = Intent(this, FeatureActivity::class.java)
            startActivity(intent)
        }

        val btnModel = findViewById<CardView>(R.id.btnModel) // Use CardView
        btnModel.setOnClickListener {
            val intent = Intent(this, ModelActivity::class.java)
            startActivity(intent)
        }

        val btnSimulation = findViewById<CardView>(R.id.btnSimulation) // Use CardView
        btnSimulation.setOnClickListener {
            val intent = Intent(this, SimulationActivity::class.java)
            startActivity(intent)
        }

        back = findViewById(R.id.btnBack)
        back.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.btnBack -> run {
                    val backIntent = Intent(this@MenuActivity, MainActivity::class.java)
                    startActivity(backIntent)
                }
            }
        }
    }
}
