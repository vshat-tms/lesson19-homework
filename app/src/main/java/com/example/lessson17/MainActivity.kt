package com.example.lessson17

import android.content.Context
import android.content.res.Resources
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
        val minNumRandom = resources.getInteger(R.integer.min_rnd)
        val maxNumRandom = resources.getInteger(R.integer.max_rnd)
        val countStep = resources.getInteger(R.integer.counter_step)

        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - countStep)
        }
        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + countStep)
        }
        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            updateCounter((minNumRandom..maxNumRandom).random())
        }
        findViewById<View>(R.id.btn_counter_0).setOnClickListener {
            updateCounter(resources.getInteger(R.integer.counter_zero))
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
            updateRotation(rotation + 90)
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setBg(view: View) {
        val colorText = when ((view as Button).text) {
            resources.getString(R.string.one) -> getColor(R.color.grey)
            resources.getString(R.string.two) -> getColor(R.color.grey_2)
            resources.getString(R.string.three) -> getColor(R.color.grey_3)
            else -> getColor(R.color.white)
        }
        rootView.setBackgroundColor(colorText)

        Log.d(TAG, "button click ${view.text}")
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

        Log.d(TAG, "button click $text ")
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        private lateinit var appContext: Context
        private val IMAGES_MAP by lazy {
            mapOf(
                appContext.getString(R.string.cat) to R.drawable.cat,
                appContext.getString(R.string.dog) to R.drawable.dog,
                appContext.getString(R.string.parrot) to R.drawable.parrot,
            )
        }
        private const val TAG = "myApplication"
    }

    fun info(view: View) {
        when ((view as Button).text) {
            resources.getString(R.string.device) -> {
                infoTextView.text = android.os.Build.MANUFACTURER + " " + android.os.Build.MODEL

            }
            resources.getString(R.string.time) -> {
                infoTextView.text = SimpleDateFormat("HH:mm", Locale.US).format(Date())
            }
            resources.getString(R.string.toast) -> {
                Toast.makeText(
                    this, resources.getString(R.string.message_Toast),
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> error(resources.getString(R.string.unknown_command))
        }

        Log.d(TAG, "button click ${view.text}")
    }
}