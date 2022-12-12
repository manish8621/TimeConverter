package com.mk.timeconverter

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.format.DateFormat
import android.text.format.DateUtils
import android.util.Log
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.mk.timeconverter.databinding.ActivityMain2Binding
import java.util.Calendar
import java.util.Date
import kotlin.math.abs

const val day = 0
const val mon = 1
const val year = 2
const val hr = 3
const val min = 4
const val sec = 5
class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        setOnclickListeners()
    }

    @SuppressLint("NewApi")
    private fun setOnclickListeners() {
        binding.doneBtn.setOnClickListener{
            if(binding.timeStampEt.text.isEmpty()) return@setOnClickListener

            //parse text
            val input = binding.timeStampEt.text.toString().split(" ").map { it.toInt() }

            if(input.size == 6){
                //current time
                val currentTimeMills = Calendar.getInstance().timeInMillis
                //input time
                val inputTimeMills = Calendar.getInstance().also { it.set(input[year],input[mon]-1,input[day],input[hr],input[min],input[sec]) }.let {
                    it.timeInMillis
                }

                //calculate sec diff
                val diff = (currentTimeMills - inputTimeMills)/1000

                //get duration
                val duration = convertMillisTODuration(diff)

                binding.outTv.text = duration.toString()
            }
            else{
                Toast.makeText(this, "invalid input", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun convertMillisTODuration(_diff: Long):Duration {
        val duration = Duration()
        var diff = _diff
        if(diff<0){
            diff*=-1
            duration.suffix=" before"
        }
        else if (diff>0)
            duration.suffix = " ago"
        else
            duration.suffix = "its current time"


        //remaining secs
        duration.sec = diff%60
        diff -= duration.sec
        if(diff==0L) return duration


        //remaining min
        var mins = diff/60
        duration.min = ((mins%60).toInt())
        mins -= duration.min

        if(mins == 0L) return duration

        //remaining hours
        var hours = mins/60
        duration.hour = (hours%24).toInt()
        hours -= duration.hour

        if(hours == 0L) return duration

        //remaining days
        var days = hours/24
        duration.days = (days%30).toInt()
        days -= duration.days

        //remaining weeks
        if(duration.days>=7)
        {
            (duration.days / 7).let {
                duration.weeks = it
                duration.days = duration.days % 7
            }
        }

        if(days == 0L) return duration





        //remaining months
        var month = days/30
        duration.month = (month%12).toInt()
        month -= duration.month
        if(month == 0L) return duration


        //years
        duration.year = (month/12).toInt()


        return duration
    }



    data class Duration(var days:Int=0,var month:Int=0,var weeks:Int=0,var year:Int=0,var hour:Int=0,var min:Int=0,var sec:Long=0,var suffix:String=""){
        override fun toString(): String {
            val sb = StringBuilder()
            if(sec>0)
                sb.append("$sec second${if(sec>1)"s" else ""} ")
            if(min>0)
                sb.append("$min min${if(min>1)"s" else ""} ")
            if(hour>0)
                sb.append("$hour hour${if(hour>1)"s" else ""} ")
            if(days>0)
                sb.append("$days day${if(days>1)"s" else ""} ")
            if(month>0)
                sb.append("$month month${if(month>1)"s" else ""} ")
            if(weeks>0)
                sb.append("$weeks week${if(weeks>1)"s" else ""} ")
            if(year>0)
                sb.append("$year year${if(year>1)"s" else ""} ")
            sb.append(suffix)

            return sb.toString()
        }
    }
}