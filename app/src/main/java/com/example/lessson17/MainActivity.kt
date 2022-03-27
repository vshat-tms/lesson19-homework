package com.example.lessson17

import android.content.ContentValues.TAG
import android.content.Context
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
import kotlin.math.log

class MainActivity : AppCompatActivity() {
    private var counter = 0
    private var rotation = 0f

    @DrawableRes
    private var currentImageRes = R.drawable.cat
    private lateinit var infoTextView: TextView
    private lateinit var rootView: View
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        appContext = applicationContext
        rootView = findViewById(R.id.root)
        infoTextView = findViewById(R.id.tv_info)
        imageView = findViewById(R.id.imageView)


        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - resources.getInteger(R.integer.counter_step))
        }
        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + resources.getInteger(R.integer.counter_step))

        }
        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            val rangeForRND =
                resources.getInteger(R.integer.min_value_for_rnd)..resources.getInteger(R.integer.max_value_for_rnd)
            updateCounter((rangeForRND).random())

        }
        findViewById<View>(R.id.btn_counter_0).setOnClickListener {
            updateCounter(resources.getInteger(R.integer.start_value_for_counter))
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
            updateRotation(rotation + resources.getInteger(R.integer.swivel_angle_by_click).toFloat())
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
            TAG, "click on button ${view.text} set color $colorText"
        )
    }

    fun setImage(view: View) {
        val text = (view as Button).text
        var imageRes = IMAGES_MAP[text]
        if (imageRes == null) {
            imageRes = (IMAGES_MAP.values - currentImageRes).random()
        }
        updateRotation(resources.getInteger(R.integer.swivel_angle_start).toFloat())
        currentImageRes = imageRes
        imageView.setImageResource(imageRes)
        Log.d(
            TAG, "click on button ${view.text} set image $imageRes"
        )
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        private lateinit  var appContext: Context
        private val IMAGES_MAP by lazy {
            mapOf(
                appContext.getString(R.string.cat) to R.drawable.cat,
                appContext.getString(R.string.dog) to R.drawable.dog,
                appContext.getString(R.string.parrot) to R.drawable.parrot,
            )
        }
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
            TAG, "click on button ${view.text}"
        )
    }
}