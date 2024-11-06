package com.example.bitfit2

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import java.math.BigDecimal
import java.math.RoundingMode

class WorkoutSumFragment (private val entryRecord: List<EntryRecord>): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_sum, container, false)
        if (entryRecord.isNotEmpty()){
            updateView(view, entryRecord)
        }else{
        }
        return view
    }

    private fun updateView(view: View, entryRecord: List<EntryRecord>){
        val avgCalories = view.findViewById(R.id.Acalories) as TextView
        val avgTime = view.findViewById(R.id.Atime) as TextView
        val totalCalories = view.findViewById(R.id.Tcalories) as TextView
        val totalTime = view.findViewById(R.id.Ttime) as TextView
        val totalWorkout = view.findViewById(R.id.workout) as TextView

        val totalRecords = entryRecord.size
        var tCalories = 0
        var tTime = 0.0
        entryRecord.forEach{
            if (it.exerciseCalorie != null && it.exerciseTime != null){
                tCalories += it.exerciseCalorie
                tTime += it.exerciseTime

            }
        }
        var aCalories = BigDecimal(tCalories/totalRecords).setScale(2, RoundingMode.HALF_UP).toDouble()
        Log.v("here", aCalories.toString())
        var aTime = BigDecimal(tTime/totalRecords).setScale(2, RoundingMode.HALF_UP).toDouble()
        avgCalories.text = "$aCalories Cal."
        avgTime.text = "$aTime mins."
        if (totalRecords == 1){
            totalWorkout.text = "$totalRecords workout"
        } else{
            totalWorkout.text = "$totalRecords workouts"
        }
        totalCalories.text = "$tCalories Cal."
        totalTime.text = "$tTime mins."


    }
}