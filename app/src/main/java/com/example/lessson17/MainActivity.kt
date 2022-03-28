package com.example.lessson17

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

        val MIN_VALUE = resources.getInteger(R.integer.minValues)
        val MAX_VALUE = resources.getInteger(R.integer.maxValues)

        draw = applicationContext

        rootView = findViewById(R.id.root)
        infoTextView = findViewById(R.id.tv_info)
        imageView = findViewById(R.id.imageView)


        findViewById<View>(R.id.btn_counter_minus).setOnClickListener {
            updateCounter(counter - 1)
        }
        findViewById<View>(R.id.btn_counter_plus).setOnClickListener {
            updateCounter(counter + 1)

        }
        findViewById<View>(R.id.btn_counter_rnd).setOnClickListener {
            updateCounter((MIN_VALUE..MAX_VALUE).random())

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
            updateRotation(rotation + 90)
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    fun setBg(view: View) {
        val colorText = when ((view as Button).text) {
            "1" -> getColor(R.color.white_204)
            "2" -> getColor(R.color.white_221)
            "3" -> getColor(R.color.white_238)
            else -> getColor(R.color.white)
        }
        rootView.setBackgroundColor(colorText)

        Log.d(TAG, "Button click ${view.text}")
    }

    fun setImage(view: View) {
        val text = (view as Button).text
        var imageRes = IMAGES_MAP[text]
        if (imageRes == null) {
            imageRes = (IMAGES_MAP.values - currentImageRes).random()
        }
        updateRotation(0f)
        currentImageRes = imageRes
        imageView.setImageResource(imageRes)

        Log.d(TAG, "Button click ${view.text}")
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {

        private const val TAG = "PrivateActivity"

        private lateinit var draw: Context
        private val IMAGES_MAP by lazy {
            mapOf(
                draw.getString(R.string.button_cat) to R.drawable.cat,
                draw.getString(R.string.button_dog) to R.drawable.dog,
                draw.getString(R.string.button_parrot) to R.drawable.parrot
            )
        }
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
}