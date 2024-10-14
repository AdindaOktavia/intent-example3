package id.ac.polbeng.adindaoktavia.intentexample3

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.ac.polbeng.adindaoktavia.intentexample3.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySecondBinding
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val bundle = intent.getBundleExtra("main_activity_data")
        val height = bundle?.getDouble(/* key = */ "height")
        val weight = bundle?.getDouble("weight")
        "Height: $height | Weight: $weight".also { binding.txtIntentData.text = it }
        binding.btnCalculate.setOnClickListener {

            val intent = Intent()
            var bmiValue = 0.0
            if (height != null && weight != null){
                bmiValue = 703 * (weight / (height * height))
            }
            intent.putExtra("second_activity_data", bmiValue)
            setResult(Activity.RESULT_OK, intent)
            finish()
        }
    }
}
