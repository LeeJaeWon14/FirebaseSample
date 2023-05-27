package com.example.firebasesample

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log

class MyService : Service() {
    private val TAG = javaClass.simpleName
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val receiver = MyReceiver()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.e(TAG, "onStartCommand()")

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onTaskRemoved(rootIntent: Intent?) {
        super.onTaskRemoved(rootIntent)

        Log.e(TAG, "onTaskRemoved()")
        Log.e(TAG, "This task removed from task list!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.e(TAG, "onDestroy()")
    }
}