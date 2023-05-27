package com.example.firebasesample

import android.app.Application
import android.util.Log

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Log.e(javaClass.simpleName, "onCreate()")

        Thread.setDefaultUncaughtExceptionHandler { _, t ->
            Log.e(javaClass.simpleName, "UncaughtExceptionHandler: exception >> ${t.message}")
        }
    }
}