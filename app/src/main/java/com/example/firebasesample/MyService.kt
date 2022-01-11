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

        createNotificationChannel()
        getNotificationBuilder(getString(R.string.app_name), "Start service")

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

    private fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "A",
                "Test",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = getColor(R.color.purple_500)
                enableVibration(true)
                description = "Notification"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationBuilder(title: String, text: String) : NotificationCompat.Builder {
        val notificationPendingIntent = PendingIntent.getActivity(
            this,
            1,
            Intent(this, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        return NotificationCompat.Builder(this, "").apply {
            setContentTitle(title)
            setContentText(text)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentIntent(notificationPendingIntent)
            setAutoCancel(true)
        }
    }
}