package com.example.bitfit2

import android.app.Application

class ExerciseApp: Application() {
    val db by lazy {AppDatabase.getInstance(this)}
}