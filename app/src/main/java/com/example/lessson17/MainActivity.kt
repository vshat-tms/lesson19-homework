package com.example.lessson17

import android.graphics.Color
import android.os.Bundle
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
    private var counter = DEFAULT_COUNTER_VALUE
    private var rotation = IMAGE_DEFAULT_ROTATION

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

        imagesMap = mapOf (
            getString(R.string.cat) to R.drawable.cat,
            getString(R.string.dog) to R.drawable.dog,
            getString(R.string.parrot) to R.drawable.parrot
        )

        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - COUNTER_STEP)
        }
        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + COUNTER_STEP)

        }
        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            val randomRange =
                resources.getInteger(R.integer.min_random_value)..resources.getInteger(R.integer.max_random_value)
            updateCounter(randomRange.random())

        }
        findViewById<View>(R.id.btn_counter_0).setOnClickListener {
            updateCounter(DEFAULT_COUNTER_VALUE)
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
            updateRotation(rotation + IMAGE_ROTATION_VALUE_BY_CLICK)
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setBg(view: View) {
        val colorText = when ((view as Button).text) {
            getString(R.string.background_color_yellow) -> getColor(R.color.yellow)
            getString(R.string.background_color_purple) -> getColor(R.color.purple)
            getString(R.string.background_color_cyan) -> getColor(R.color.cyan)
            else -> getColor(R.color.khaki)
        }
        rootView.setBackgroundColor(colorText)
    }

    fun setImage(view: View) {
        val text = (view as Button).text
        var imageRes = imagesMap[text]
        if (imageRes == null) {
            imageRes = (imagesMap.values - currentImageRes).random()
        }
        updateRotation(IMAGE_DEFAULT_ROTATION)
        currentImageRes = imageRes
        imageView.setImageResource(imageRes)
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        private const val COUNTER_STEP = 1
        private const val DEFAULT_COUNTER_VALUE = 0
        private const val IMAGE_ROTATION_VALUE_BY_CLICK = 90f
        private const val IMAGE_DEFAULT_ROTATION = 0f
    }

    fun info(view: View) {
        when ((view as Button).text) {
            getString(R.string.device) -> {
                infoTextView.text = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL

            }
            getString(R.string.time) -> {
                infoTextView.text =
                    SimpleDateFormat("HH:mm", Locale.US).format(Date())
            }
            getString(R.string.toast) -> {
                Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_LONG).show()
            }
            else -> error(R.string.unknown_command)
        }
    }
}