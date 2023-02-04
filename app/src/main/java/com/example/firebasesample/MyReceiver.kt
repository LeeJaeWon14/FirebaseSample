package com.example.firebasesample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            when(it.action) {
                Intent.ACTION_SHUTDOWN -> { Log.e("TEST", "Shut down") }
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    Toast.makeText(context, "AIRPLANE MODE", Toast.LENGTH_SHORT).show()
                    NotifyHelper.notify(context!!, this.javaClass.simpleName, "AIRPLANE MODE")
                }
                else -> {}
            }
        }
    }
}