package com.rafil.diseasealert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class DatasetActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dataset)
    }
    fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.btnBack -> run {
                    val backIntent = Intent(this@DatasetActivity, MenuActivity::class.java)
                    startActivity(backIntent)
                }
            }
        }
    }
}