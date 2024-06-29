package com.rafil.diseasealert

import android.content.Intent
import android.content.res.AssetManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import org.tensorflow.lite.Interpreter
import java.io.FileInputStream
import java.nio.MappedByteBuffer
import java.nio.channels.FileChannel

class SimulationActivity : AppCompatActivity() {

    private lateinit var interpreter: Interpreter
    private val mModelPath = "disease.tflite"

    private lateinit var resultText: TextView
    private lateinit var Glucose: EditText
    private lateinit var Cholesterol: EditText
    private lateinit var Hemoglobin: EditText
    private lateinit var Platelets: EditText
    private lateinit var White_Blood_Cells: EditText
    private lateinit var Red_Blood_Cells: EditText
    private lateinit var Hematocrit: EditText
    private lateinit var Mean_Corpuscular_Volume: EditText
    private lateinit var Mean_Corpuscular_Hemoglobin: EditText
    private lateinit var Mean_Corpuscular_Hemoglobin_Concentration: EditText
    private lateinit var Insulin: EditText
    private lateinit var BMI: EditText
    private lateinit var Systolic_Blood_Pressure: EditText
    private lateinit var Diastolic_Blood_Pressure: EditText
    private lateinit var Triglycerides: EditText
    private lateinit var HbA1c: EditText
    private lateinit var LDL_Cholesterol: EditText
    private lateinit var HDL_Cholesterol: EditText
    private lateinit var ALT: EditText
    private lateinit var AST: EditText
    private lateinit var Heart_Rate: EditText
    private lateinit var Creatinine: EditText
    private lateinit var Troponin: EditText
    private lateinit var C_reactive_Protein: EditText
    private lateinit var Predict: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)

        resultText = findViewById(R.id.txtResult)
        Glucose = findViewById(R.id.Glucose)
        Cholesterol = findViewById(R.id.Cholesterol)
        Hemoglobin = findViewById(R.id.Hemoglobin)
        Platelets = findViewById(R.id.Platelets)
        White_Blood_Cells = findViewById(R.id.White_Blood_Cells)
        Red_Blood_Cells = findViewById(R.id.Red_Blood_Cells)
        Hematocrit = findViewById(R.id.Hematocrit)
        Mean_Corpuscular_Volume = findViewById(R.id.Mean_Corpuscular_Volume)
        Mean_Corpuscular_Hemoglobin = findViewById(R.id.Mean_Corpuscular_Hemoglobin)
        Mean_Corpuscular_Hemoglobin_Concentration = findViewById(R.id.Mean_Corpuscular_Hemoglobin_Concentration)
        Insulin = findViewById(R.id.Insulin)
        BMI = findViewById(R.id.BMI)
        Systolic_Blood_Pressure = findViewById(R.id.Systolic_Blood_Pressure)
        Diastolic_Blood_Pressure = findViewById(R.id.Diastolic_Blood_Pressure)
        Triglycerides = findViewById(R.id.Triglycerides)
        HbA1c = findViewById(R.id.HbA1c)
        LDL_Cholesterol = findViewById(R.id.LDL_Cholesterol)
        HDL_Cholesterol = findViewById(R.id.HDL_Cholesterol)
        ALT = findViewById(R.id.ALT)
        AST = findViewById(R.id.AST)
        Heart_Rate = findViewById(R.id.Heart_Rate)
        Creatinine = findViewById(R.id.Creatinine)
        Troponin = findViewById(R.id.Troponin)
        C_reactive_Protein = findViewById(R.id.C_reactive_Protein)
        Predict = findViewById(R.id.btnCheck)

        Predict.setOnClickListener {
            Log.d("SimulationActivity", "Predict button clicked")

            // Validate inputs
            if (!validateInputs()) {
                resultText.text = "Harap Isi dahulu semua Pertanyaan dengan benar"
                return@setOnClickListener
            }

            var result = doInference(
                Glucose.text.toString(),
                Cholesterol.text.toString(),
                Hemoglobin.text.toString(),
                Platelets.text.toString(),
                White_Blood_Cells.text.toString(),
                Red_Blood_Cells.text.toString(),
                Hematocrit.text.toString(),
                Mean_Corpuscular_Volume.text.toString(),
                Mean_Corpuscular_Hemoglobin.text.toString(),
                Mean_Corpuscular_Hemoglobin_Concentration.text.toString(),
                Insulin.text.toString(),
                BMI.text.toString(),
                Systolic_Blood_Pressure.text.toString(),
                Diastolic_Blood_Pressure.text.toString(),
                Triglycerides.text.toString(),
                HbA1c.text.toString(),
                LDL_Cholesterol.text.toString(),
                HDL_Cholesterol.text.toString(),
                ALT.text.toString(),
                AST.text.toString(),
                Heart_Rate.text.toString(),
                Creatinine.text.toString(),
                Troponin.text.toString(),
                C_reactive_Protein.text.toString()
            )

            Log.d("SimulationActivity", "Inference result: $result")

            runOnUiThread {
                when (result) {
                    0 -> resultText.text = "Anda Memiliki Gejala Anemia, Silahkan konsultasikan dengan Dokter"
                    1 -> resultText.text = "Anda Memiliki Gejala Diabetes, Silahkan konsultasikan dengan Dokter"
                    2 -> resultText.text = "Anda Tidak memiliki gejala penyakit apapun, lanjutkan pola hidup sehat anda"
                    3 -> resultText.text = "Anda Memiliki gejala Penyakit Jantung, Segera hubungi Dokter !"
                    4 -> resultText.text = "Anda memiliki gejala penyakit Thalasse, Silahkan konsultasikan dengan Dokter"
                    else -> resultText.text = "Anda Memiliki gejala penyakit Thromboc, Silahkan konsultasikan dengan dokter"
                }
            }
        }
        initInterpreter()
    }

    private fun validateInputs(): Boolean {
        // Check if all inputs are filled and valid
        val inputs = listOf(
            Glucose, Cholesterol, Hemoglobin, Platelets, White_Blood_Cells,
            Red_Blood_Cells, Hematocrit, Mean_Corpuscular_Volume,
            Mean_Corpuscular_Hemoglobin, Mean_Corpuscular_Hemoglobin_Concentration,
            Insulin, BMI, Systolic_Blood_Pressure, Diastolic_Blood_Pressure,
            Triglycerides, HbA1c, LDL_Cholesterol, HDL_Cholesterol,
            ALT, AST, Heart_Rate, Creatinine, Troponin, C_reactive_Protein
        )

        return inputs.all { it.text.toString().isNotEmpty() }
    }

    private fun initInterpreter() {
        val options = Interpreter.Options()
        options.setNumThreads(4)  // Using 4 threads instead of 25
        options.setUseNNAPI(true)
        interpreter = Interpreter(loadModelFile(assets, mModelPath), options)
    }

    private fun doInference(
        input1: String,
        input2: String,
        input3: String,
        input4: String,
        input5: String,
        input6: String,
        input7: String,
        input8: String,
        input9: String,
        input10: String,
        input11: String,
        input12: String,
        input13: String,
        input14: String,
        input15: String,
        input16: String,
        input17: String,
        input18: String,
        input19: String,
        input20: String,
        input21: String,
        input22: String,
        input23: String,
        input24: String
    ): Int {
        val inputVal = FloatArray(24)

        inputVal[0] = input1.toFloat()
        inputVal[1] = input2.toFloat()
        inputVal[2] = input3.toFloat()
        inputVal[3] = input4.toFloat()
        inputVal[4] = input5.toFloat()
        inputVal[5] = input6.toFloat()
        inputVal[6] = input7.toFloat()
        inputVal[7] = input8.toFloat()
        inputVal[8] = input9.toFloat()
        inputVal[9] = input10.toFloat()
        inputVal[10] = input11.toFloat()
        inputVal[11] = input12.toFloat()
        inputVal[12] = input13.toFloat()
        inputVal[13] = input14.toFloat()
        inputVal[14] = input15.toFloat()
        inputVal[15] = input16.toFloat()
        inputVal[16] = input17.toFloat()
        inputVal[17] = input18.toFloat()
        inputVal[18] = input19.toFloat()
        inputVal[19] = input20.toFloat()
        inputVal[20] = input21.toFloat()
        inputVal[21] = input22.toFloat()
        inputVal[22] = input23.toFloat()
        inputVal[23] = input24.toFloat()

        val output = Array(1) { FloatArray(6) }
        interpreter.run(inputVal, output)
        return output[0][0].toInt()
    }

    private fun loadModelFile(assetManager: AssetManager, modelPath: String): MappedByteBuffer {
        val fileDescriptor = assetManager.openFd(modelPath)
        val inputStream = FileInputStream(fileDescriptor.fileDescriptor)
        val fileChannel = inputStream.channel
        val startOffset = fileDescriptor.startOffset
        val declaredLength = fileDescriptor.declaredLength
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
    }
    fun onClick(v: View?) {
        if (v != null) {
            when(v.id){
                R.id.btnBack -> run {
                    val backIntent = Intent(this@SimulationActivity, MenuActivity::class.java)
                    startActivity(backIntent)
                }
            }
        }
    }
}
