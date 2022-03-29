package com.example.lessson17

import android.content.Context
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
    private var counter = 0
    private val counterStep = resources.getInteger(R.integer.counter_step)
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

        imageView.setOnClickListener {
            updateRotation(
                rotation + resources.getInteger(R.integer.swivel_angle_by_click).toFloat()
            )
        }
    }

    fun counterOperations(view: View){
        when((view as Button).text){
            getString(R.string.plus) -> updateCounter(counter + counterStep)
            getString(R.string.minus) -> updateCounter(counter - counterStep)
            getString(R.string.zero) -> updateCounter(resources.getInteger(R.integer.default_counter_value))
            getString(R.string.rnd) -> updateCounter(getRandomValueFromRange())
        }
    }

    private fun updateCounter(value: Int) {
        counter = value
        infoTextView.text = value.toString()
    }

    private fun getRandomValueFromRange(): Int{
        val randomNumberRange =
            resources.getInteger(R.integer.min_value_for_random)..resources.getInteger(R.integer.max_value_for_random)
        return randomNumberRange.random()
    }

    fun setText (view: View){
        val textColor = when((view as Button).text){
            getString(R.string.red_text_color_symbol) -> getColor(R.color.text_red)
            getString(R.string.green_text_color_symbol) -> getColor(R.color.text_green)
            getString(R.string.blue_text_color_symbol) -> getColor(R.color.text_blue)
            getString(R.string.magenta_text_color_symbol) -> getColor(R.color.text_magenta)
            else -> getColor(R.color.black)
        }
        infoTextView.setTextColor(textColor)
    }

    fun setBg(view: View) {
        val colorBg = when ((view as Button).text) {
            getString(R.string.background_color_number_1) -> getColor(R.color.background_gray)
            getString(R.string.background_color_number_2) -> getColor(R.color.background_light_gray)
            getString(R.string.background_color_number_3) -> getColor(R.color.background_ivory)
            else -> getColor(R.color.background_white)
        }
        rootView.setBackgroundColor(colorBg)
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
    }

    private fun updateRotation(angle: Float) {
        rotation = angle
        imageView.rotation = rotation
    }

    companion object {
        private lateinit var appContext: Context
        private val IMAGES_MAP = mapOf(
            appContext.getString(R.string.cat) to R.drawable.cat,
            appContext.getString(R.string.dog) to R.drawable.dog,
            appContext.getString(R.string.parrot) to R.drawable.parrot
        )
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
                Toast.makeText(this, getString(R.string.toast_message), Toast.LENGTH_LONG).show()
            }
            else -> error(getString(R.string.unknown_command))
        }
    }
}