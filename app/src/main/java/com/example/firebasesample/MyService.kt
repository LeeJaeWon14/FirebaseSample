package com.example.firebasesample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class MyService : Service() {
    private lateinit var notificationManager: NotificationManager
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private val receiver = MyReceiver()
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Toast.makeText(this, "Start service", Toast.LENGTH_SHORT).show()

        registerReceiver(receiver, IntentFilter().apply {
            addAction("android.intent.action.ACTION_SHUTDOWN")
            addAction("android.intent.action.AIRPLANE_MODE")
        })

        return START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(this, "Stop service", Toast.LENGTH_SHORT).show()
        unregisterReceiver(receiver)
    }
}