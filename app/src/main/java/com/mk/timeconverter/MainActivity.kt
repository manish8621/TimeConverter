package com.mk.timeconverter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TimePicker
import android.widget.TimePicker.OnTimeChangedListener
import android.widget.Toast
import com.mk.timeconverter.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    lateinit var binding :ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setTimePicker()
        binding.nxtBtn.setOnClickListener{
            Intent(this,MainActivity2::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun setTimePicker() {
        binding.timePickerView.setIs24HourView(false)
        binding.timePickerView.setOnTimeChangedListener { view, hourOfDay, minute ->
            val time = "$hourOfDay : $minute Hrs"
            binding.outputValueTv.text = time
        }
    }
}