package com.example.lessson17

import android.content.ContentValues.TAG
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
    private var counter = 0
    private var rotation = 0F

    @DrawableRes
    private var currentImageRes = R.drawable.cat

    private lateinit var infoTextView: TextView
    private lateinit var rootView: View
    private lateinit var imageView: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rootView = findViewById(R.id.root)
        infoTextView = findViewById(R.id.tv_info)
        imageView = findViewById(R.id.imageView)


        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - COUNTER_CHANGE)
        }
        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + COUNTER_CHANGE)
        }
        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            updateCounter(
                (resources.getInteger(R.integer.min_rnd_range)..
                        resources.getInteger(R.integer.max_rnd_range)).random()
            )
        }
        findViewById<View>(R.id.btn_counter_0).setOnClickListener {
            updateCounter(INITIAL_POSITION_IMAGE)
        }
        imageView.setOnClickListener {
            updateRotation(rotation + ROTATION_ANGEL)
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setColorText(view: View) {
        val colorText = when ((view as Button).text) {
            getString(R.string.text_btn_r) -> getColor(R.color.btn_color_r)
            getString(R.string.text_btn_g) -> getColor(R.color.btn_color_g)
            getString(R.string.text_btn_b) -> getColor(R.color.btn_color_b)
            else -> getColor(R.color.btn_color_m)
        }
        infoTextView.setTextColor(colorText)

        Log.d(TAG, "User pressed button: ${view.text}")
    }

    fun setBg(view: View) {
        val colorBackground = when ((view as Button).text) {
            getString(R.string.text_btn_1) -> getColor(R.color.background_color_1)
            getString(R.string.text_btn_2) -> getColor(R.color.background_color_2)
            getString(R.string.text_btn_3) -> getColor(R.color.background_color_3)
            else -> getColor(R.color.background_color_4)
        }
        rootView.setBackgroundColor(colorBackground)

        Log.d(TAG, "User pressed button: ${view.text}")
    }

    fun setImage(view: View) {
        val text = (view as Button).text
        var imageRes = IMAGES_MAP[text]
        if (imageRes == null) {
            imageRes = (IMAGES_MAP.values - currentImageRes).random()
        }
        updateRotation(0F)
        currentImageRes = imageRes
        imageView.setImageResource(imageRes)

        Log.d(TAG, "User pressed button: ${view.text}")
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        private val COUNTER_CHANGE = 1
        private val ROTATION_ANGEL = 90
        private val INITIAL_POSITION_IMAGE = 0
        private val IMAGES_MAP = mapOf(
            "cat" to R.drawable.cat,
            "dog" to R.drawable.dog,
            "parrot" to R.drawable.parrot,
        )
        private const val TAG = "MainActivity"
    }

    fun info(view: View) {
        when ((view as Button).text) {
            "device" -> {
                infoTextView.text = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL
            }
            "time" -> {
                infoTextView.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
            }
            "toast" -> {
                Toast.makeText(this, "hello", Toast.LENGTH_LONG).show()
            }
            else -> error("unknown command")
        }
        Log.d(TAG, "User pressed button: ${view.text}")
    }
}