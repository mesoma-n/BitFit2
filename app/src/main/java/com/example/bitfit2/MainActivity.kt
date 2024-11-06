package com.example.bitfit2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var exerciseRecordsRecyclerView: RecyclerView
    private val exerciseRecords = mutableListOf<EntryRecord>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lifecycleScope.launch {
            (application as ExerciseApp).db.exerciseDao().getAll().collect { databaseList ->
                databaseList.map { entity ->
                    EntryRecord(
                        entity.exerciseName,
                        entity.exerciseCalorie,
                        entity.exerciseTime
                    )
                }.also { mappedList ->
                    exerciseRecords.clear()
                    exerciseRecords.addAll(mappedList)
                    Log.v("done", "added")
                    Log.v("test", exerciseRecords.toString())
                }
            }
        }

        val workoutRecordFragment: Fragment = WorkoutRecordFragment(this)
        val workoutSumFragment: Fragment = WorkoutSumFragment(exerciseRecords)
        val buttonNavView: BottomNavigationView = findViewById(R.id.nav)

        buttonNavView.setOnItemSelectedListener {
            lateinit var fragment: Fragment
            when(it.itemId){
                R.id.workouts -> fragment = workoutRecordFragment
                R.id.summary -> fragment = workoutSumFragment
            }
            replaceFragment(fragment)
            true
        }
        buttonNavView.selectedItemId = R.id.workouts

        val addSessionBtn = findViewById<Button>(R.id.button)

        addSessionBtn.setOnClickListener {
            // launch the detail activity
            val intent = Intent(this, RecordActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame, newFragment)
        fragmentTransaction.commit()
    }
}