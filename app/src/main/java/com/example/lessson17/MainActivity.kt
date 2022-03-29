package com.example.lessson17

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {
    private var counter = START_VALUE_FOR_COUNTER
    private var rotation = IMAGE_SWIVEL_ANGLE_START

    @DrawableRes
    private var currentImageRes = R.drawable.cat
    private lateinit var infoTextView: TextView
    private lateinit var rootView: View
    private lateinit var imageView: ImageView
    private lateinit var imagesMap: Map<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rootView = findViewById(R.id.root)
        infoTextView = findViewById(R.id.tv_info)
        imageView = findViewById(R.id.imageView)

        imagesMap = mapOf(
            getString(R.string.cat) to R.drawable.cat,
            getString(R.string.dog) to R.drawable.dog,
            getString(R.string.parrot) to R.drawable.parrot,
        )

        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - COUNTER_STEP)
        }
        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + COUNTER_STEP)

        }
        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            val rangeForRND =
                resources.getInteger(R.integer.min_value_for_rnd)..resources.getInteger(R.integer.max_value_for_rnd)
            updateCounter((rangeForRND).random())

        }
        findViewById<View>(R.id.btn_counter_0).setOnClickListener {
            updateCounter(START_VALUE_FOR_COUNTER)
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
            updateRotation(
                rotation + IMAGE_SWIVEL_ANGLE_INCREASE_BY_CLICK
            )
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setBg(view: View) {
        val colorText = when ((view as Button).text) {
            getString(R.string.number_1) -> getColor(R.color.background_gray)
            getString(R.string.number_2) -> getColor(R.color.background_light_gray)
            getString(R.string.number_3) -> getColor(R.color.background_ivory)
            else -> getColor(R.color.background_white)
        }
        rootView.setBackgroundColor(colorText)
        Log.d(
            "setBG", "click on button ${view.text} set color $colorText"
        )
    }

    fun setImage(view: View) {
        val text = (view as Button).text
        var imageRes = imagesMap[text]
        if (imageRes == null) {
            imageRes = (imagesMap.values - currentImageRes).random()
        }
        updateRotation(IMAGE_SWIVEL_ANGLE_START)
        currentImageRes = imageRes
        imageView.setImageResource(imageRes)
        Log.d(
            "setImage", "click on button ${view.text} set image $imageRes"
        )
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        private const val COUNTER_STEP = 1
        private const val START_VALUE_FOR_COUNTER = 0
        private const val IMAGE_SWIVEL_ANGLE_INCREASE_BY_CLICK = 90f
        private const val IMAGE_SWIVEL_ANGLE_START = 0f
    }

    fun info(view: View) {
        when ((view as Button).text) {
            getString(R.string.device) -> {
                infoTextView.text = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
            }
            getString(R.string.time) -> {
                infoTextView.text =
                    SimpleDateFormat(getString(R.string.date_format), Locale.US).format(Date())
            }
            getString(R.string.toast) -> {
                Toast.makeText(this, getString(R.string.hello_message), Toast.LENGTH_LONG).show()
            }
            else -> error(getString(R.string.unknown_command))
        }
        Log.d(
            "info", "click on button ${view.text}"
        )
    }
}