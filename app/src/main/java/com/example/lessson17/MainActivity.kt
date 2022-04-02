package com.example.lessson17

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
    private var counter = ENTRY_COUNTER
    private var rotation = IMAGE_ANGLE_START

    @DrawableRes
    private var currentImageRes = R.drawable.cat
    private lateinit var infoTextView: TextView
    private lateinit var rootView: View
    private lateinit var imageView: ImageView
    private lateinit var imageZooMap: Map<String, Int>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val minValue = resources.getInteger(R.integer.min_value)
        val maxValue = resources.getInteger(R.integer.max_value)

        rootView = findViewById(R.id.root)
        infoTextView = findViewById(R.id.tv_info)
        imageView = findViewById(R.id.imageView)

        imageZooMap =
            mapOf(
                getString(R.string.button_cat) to R.drawable.cat,
                getString(R.string.button_dog) to R.drawable.dog,
                getString(R.string.button_parrot) to R.drawable.parrot
            )

        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - COUNTER_STEP)
        }
        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + COUNTER_STEP)

        }
        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            updateCounter((minValue..maxValue).random())

        }
        findViewById<View>(R.id.btn_counter_0).setOnClickListener {
            updateCounter(ENTRY_COUNTER)
        }

        imageView.setOnClickListener {
            updateRotation(rotation + ROTATION_BY_CLICK)
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setBg(view: View) {
        val colorBg = when ((view as Button).text) {
            getString(R.string.button_1) -> getColor(R.color.white_204)
            getString(R.string.button_2) -> getColor(R.color.white_221)
            getString(R.string.button_3) -> getColor(R.color.white_238)
            else -> getColor(R.color.white)
        }
        rootView.setBackgroundColor(colorBg)

        Log.d(TAG, "Button click ${view.text}")
    }

    fun setColorText(view: View) {
        val colorText = when ((view as Button).text) {
            getString(R.string.button_r) -> getColor(R.color.red)
            getString(R.string.button_g) -> getColor(R.color.green)
            getString(R.string.button_b) -> getColor(R.color.blue)
            getString(R.string.button_m) -> getColor(R.color.magenta)
            else -> getColor(R.color.black)
        }
        infoTextView.setTextColor(colorText)
        Log.d(TAG, "Button click ${view.text}")
    }

    fun setImage(view: View) {
        val text = ((view as Button).text)
        var imageRes = imageZooMap[text]
        if (imageRes == null) {
            imageRes = (imageZooMap.values - currentImageRes).random()
        }
        updateRotation(IMAGE_ANGLE_START)
        currentImageRes = imageRes
        imageView.setImageResource(imageRes)
        Log.d(TAG, "Button click ${view.text}")
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    fun info(view: View) {
        when ((view as Button).text) {
            resources.getString(R.string.button_device) -> {
                infoTextView.text = "${android.os.Build.MANUFACTURER} ${android.os.Build.MODEL}"
            }
            resources.getString(R.string.button_time) -> {
                infoTextView.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
            }
            resources.getString(R.string.button_toast) -> {
                Toast.makeText(this, resources.getString(R.string.hello), Toast.LENGTH_LONG).show()
            }
            else -> error(resources.getString(R.string.error))
        }
        Log.d(TAG, "Button click ${view.text}")
    }

    companion object {

        private const val TAG = "PrivateActivity"
        private const val COUNTER_STEP = 1
        private const val ENTRY_COUNTER = 0
        private const val ROTATION_BY_CLICK = 90f
        private const val IMAGE_ANGLE_START = 0f
    }
}
