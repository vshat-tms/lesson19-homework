package com.example.lessson17

import android.graphics.Color
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private var rotation = 0f

    private var currentImageRes = R.drawable.cat

    private lateinit var infoTextView: TextView
    private lateinit var rootView: View
    private lateinit var imageView: ImageView

    private lateinit var cat: String
    private lateinit var dog: String
    private lateinit var parrot: String
    private lateinit var imagesMap: Map<String, Int>

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(KEY_TEXT, infoTextView.text.toString())
        outState.putInt(KEY_COUNTER, counter)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.root)
        infoTextView = findViewById(R.id.tv_info)
        imageView = findViewById(R.id.imageView)

        cat = resources.getString(R.string.cat_btn_text)
        dog = resources.getString(R.string.dog_btn_text)
        parrot = resources.getString(R.string.parrot_btn_text)
        imagesMap = mapOf(
            cat to R.drawable.cat,
            dog to R.drawable.dog,
            parrot to R.drawable.parrot,
        )

        val savedTextValue = savedInstanceState?.getString(KEY_TEXT)
        val savedCounterValue = savedInstanceState?.getInt(KEY_COUNTER)

        if (savedTextValue != null) {
            infoTextView.text = savedTextValue
        } else if (savedCounterValue != null) {
            updateCounter(savedCounterValue)
        }

        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - 1)
        }

        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + 1)
        }

        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            val rndMinValue = resources.getString(R.string.min_rnd_value).toInt()
            val rndMaxValue = resources.getString(R.string.max_rnd_value).toInt()
            Log.d(TAG, "min = $rndMinValue $rndMaxValue")
            updateCounter((rndMinValue..rndMaxValue).random())
        }

        findViewById<View>(R.id.btn_counter_0).setOnClickListener {
            updateCounter(0)
        }

        findViewById<View>(R.id.btn_color_r).setOnClickListener {
            infoTextView.setTextColor(Color.RED)
        }

        findViewById<View>(R.id.btn_color_g).setOnClickListener {
            infoTextView.setTextColor(Color.GREEN)
        }

        findViewById<View>(R.id.btn_color_b).setOnClickListener {
            infoTextView.setTextColor(Color.BLUE)
        }

        findViewById<View>(R.id.btn_color_m).setOnClickListener {
            infoTextView.setTextColor(Color.MAGENTA)
        }

        imageView.setOnClickListener {
            updateRotation(rotation + ROTATE_INCREASE_VALUE)
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setBg(view: View) {
        val colorText = when ((view as Button).text) {
            "1" -> R.color.color_for_bg_red
            "2" -> R.color.color_for_bg_green
            "3" -> R.color.color_for_bg_light_blue
            else -> R.color.color_for_bg_magenta
        }
        rootView.setBackgroundColor(resources.getColor(colorText))
        Log.d(TAG, "$colorText was selected - ${rootView.background}")
    }

    fun setImage(view: View) {
        val text = (view as Button).text
        var imageRes = imagesMap[text]
        if (text == resources.getString(R.string.rnd_btn_text)) {
            imageRes = (imagesMap.values - currentImageRes).random()
        }
        updateRotation(0f)
        currentImageRes = imageRes!!
        imageView.setImageResource(imageRes)
        Log.d(TAG, "img $text = ${imagesMap[text]}")
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        const val ROTATE_INCREASE_VALUE = 90
        const val TAG = "ActivityMain"

        const val KEY_TEXT = "text"
        const val KEY_COUNTER = "counter"
    }

    fun info(view: View) {
        val device = resources.getString(R.string.device_btn_text)
        val time = resources.getString(R.string.time_btn_text)
        val toast = resources.getString(R.string.toast_btn_text)

        when ((view as Button).text) {
            device -> {
                infoTextView.text = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
            }
            time -> {
                infoTextView.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
            }
            toast -> {
                Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
            }
            else -> error("unknown command")
        }

        Log.d(TAG, "${view.text} was selected -- ${infoTextView.text}")
    }
}
