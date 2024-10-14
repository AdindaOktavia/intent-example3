package id.ac.polbeng.adindaoktavia.intentexample3

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.polbeng.adindaoktavia.intentexample3.databinding.ActivityMainBinding

const val SECOND_ACTIVITY = 1000

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set hint untuk input weight dan height
        binding.inputWeight.hint = "Weight (lbs)"
        binding.inputHeight.hint = "Height (inches)"

        binding.btnSendData.setOnClickListener {
            // Cek apakah input valid sebelum mengonversi ke double
            if (binding.inputWeight.text.isNullOrEmpty() || binding.inputHeight.text.isNullOrEmpty()) {
                binding.txtBMI.text = "Please fill out both fields"
            } else {
                // Kirim data ke SecondActivity
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                val bundle = Bundle()

                // Mengonversi input menjadi Double
                bundle.putDouble("weight", binding.inputWeight.text.toString().toDouble())
                bundle.putDouble("height", binding.inputHeight.text.toString().toDouble())

                intent.putExtra("main_activity_data", bundle)
                startActivityForResult(intent, SECOND_ACTIVITY)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        clearInputs()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Cek requestCode dan resultCode
        if (requestCode == SECOND_ACTIVITY && resultCode == Activity.RESULT_OK) {
            // Cek apakah data tidak null
            data?.let {
                val bmi = it.getDoubleExtra("second_activity_data", 0.0)
                val bmiString = "%.2f".format(bmi)
                binding.txtBMI.text = "BMI : $bmiString ${getBMIDescription(bmi)}"
            }
        }
    }

    private fun getBMIDescription(bmi: Double): String {
        return when (bmi) {
            in 1.0..18.5 -> "Underweight"
            in 18.6..24.9 -> "Normal weight"
            in 25.0..29.9 -> "Overweight"
            else -> "Obese"
        }
    }

    private fun clearInputs() {
        // Membersihkan input form
        binding.inputWeight.setText("")
        binding.inputHeight.setText("")
        binding.txtBMI.text = ""
    }
}
