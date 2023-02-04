package com.example.firebasesample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.util.Log
import androidx.core.app.NotificationCompat

object NotifyHelper {
    private var NOTIFICATION_ID = 0
    private const val CHANNEL_ID = "NOTIFICATION TEMPLATE"
    //    @Synchronized
    fun notify(context: Context, title: String, message: String) {
//        NOTIFICATION_ID ++
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        createNotificationChannel(notificationManager)
        val builder = getNotificationBuilder(context, title, message)
        notificationManager.notify(
            NOTIFICATION_ID,
            builder.build()
        )
    }

    private fun createNotificationChannel(notificationManager: NotificationManager) {
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                CHANNEL_ID,
                "Test",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.GRAY
                enableVibration(true)
                description = "Notification"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationBuilder(context: Context, title: String, text: String) : NotificationCompat.Builder {
        val notificationPendingIntent = PendingIntent.getActivity(
            context,
            NOTIFICATION_ID,
            Intent(context, MainActivity::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        Log.e("noti", "init builder")

        return NotificationCompat.Builder(context, CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(text)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentIntent(notificationPendingIntent)
            setAutoCancel(true)
        }
    }
}