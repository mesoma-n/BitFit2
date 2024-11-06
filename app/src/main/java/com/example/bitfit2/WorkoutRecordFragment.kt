package com.example.bitfit2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.launch

class  WorkoutRecordFragment(private val activity: MainActivity) : Fragment() {
    private val exerRecords = mutableListOf<EntryRecord>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_workout_record, container, false)
        val recyclerView = view.findViewById(R.id.exerciseRv) as RecyclerView
        val adapter = EntryRecordAdapter(exerRecords)
        val context = view.context
        lifecycleScope.launch {
            (activity.application as ExerciseApp).db.exerciseDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    EntryRecord(
                        entity.exerciseName,
                        entity.exerciseCalorie,
                        entity.exerciseTime
                    )
                }.also { mappedList ->
                    exerRecords.clear()
                    exerRecords.addAll(mappedList)
                    adapter.notifyDataSetChanged()
                }
            }
        }
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context)
        return view
    }
}