package com.example.lessson17

import android.graphics.Color
import android.nfc.Tag
import android.os.Build
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
    private var rotation = ROTATION_ANGEL

    @DrawableRes
    private var currentImageRes = R.drawable.cat

    private lateinit var infoTextView: TextView
    private lateinit var rootView: View
    private lateinit var imageView: ImageView
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

        imagesMap = mapOf(
            getString(R.string.cat_image_btn) to R.drawable.cat,
            getString(R.string.dog_image_btn) to R.drawable.dog,
            getString(R.string.parrot_image_btn) to R.drawable.parrot
        )
        val savedTextValue = savedInstanceState?.getString(KEY_TEXT)
        val savedCounterValue = savedInstanceState?.getInt(KEY_COUNTER)

        if (savedTextValue != null) {
            infoTextView.text = savedTextValue
        } else if (savedCounterValue != null) {
            updateCounter(savedCounterValue)
        }

        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - COUNTER_CHANGE)
            Log.d(TAG,"$counter")
        }

        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + COUNTER_CHANGE)
            Log.d(TAG,"$counter")
        }

        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            val randomNumber =
                resources.getInteger(R.integer.min_rnd)..resources.getInteger(R.integer.max_rnd)
            updateCounter(randomNumber.random())
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
            updateRotation(rotation + IMAGE_ROTATION)
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setBg(view: View) {
        val colorText = when ((view as Button).text) {
            getString(R.string.first_background_btn) -> getColor(R.color.gray_background)
            getString(R.string.second_background_btn) -> getColor(R.color.light_gray_background)
            getString(R.string.third_background_btn) -> getColor(R.color.ivory_background)
            else -> getColor(R.color.white_background)
        }
        rootView.setBackgroundColor(colorText)
        Log.d(TAG,"$colorText was selected ${rootView.background}")
    }

    fun setImage(view: View) {
        val text = (view as Button).text
        var imageRes = imagesMap[text]
        if (imageRes == null) {
            imageRes = (imagesMap.values - currentImageRes).random()
        }
        updateRotation(ROTATION_ANGEL)
        currentImageRes = imageRes
        imageView.setImageResource(imageRes)
        Log.d(TAG, "${imagesMap[text]}")
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        private const val ROTATION_ANGEL = 0F
        private const val COUNTER_CHANGE = 1
        private const val IMAGE_ROTATION = 90
        const val KEY_TEXT = "text"
        const val KEY_COUNTER = "counter"
        const val TAG = "ActivityMain"

    }

    fun info(view: View) {
        when ((view as Button).text) {
            getString(R.string.device_btn) -> {
                infoTextView.text = Build.BRAND + " " + Build.MODEL
            }
            getString(R.string.time_btn) -> {
                infoTextView.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
            }
            getString(R.string.toast_btn) -> {
                Toast.makeText(this, getString(R.string.toast_messagen), Toast.LENGTH_LONG).show()
            }
            else -> error(R.string.unknown_command)

        }
        Log.d(TAG, "${view.text} -- ${infoTextView.text}")
    }

}