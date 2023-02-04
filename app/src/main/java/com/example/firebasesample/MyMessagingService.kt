package com.example.firebasesample

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyMessagingService : FirebaseMessagingService() {
    private lateinit var notificationManager: NotificationManager
    companion object {
        private const val TAG = "FCM"
        private const val PRIMARY_CHANNEL_ID = "PRIMARY CHANNEL ID"
        private const val NOTIFY_ID = 0
    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
//        FirebaseInstanceId
        // manually check
        if(FirebaseMessaging.getInstance().token.result == token)
            log(FirebaseMessaging.getInstance().token.result)
        // automatically check with callback
        FirebaseMessaging.getInstance().token.addOnSuccessListener {
            // when success, call onSuccess and param is token
            // will add NotificationManager
            log(it)
        }
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
//        isRestricted
        createNotificationChannel()
        remoteMessage.notification?.let {
            notificationManager.notify(
                NOTIFY_ID,
                getNotificationBuilder(it.title!!, it.body!!).build()
            )
        }
    }

    private fun log(msg: String) = Log.i(TAG, msg)

    private fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                "Firebase Cloud Messaging",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = getColor(R.color.purple_500)
                enableVibration(true)
                description = "Notification Setting"
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun getNotificationBuilder(title: String, text: String) : NotificationCompat.Builder {
        val notificationPendingIntent = PendingIntent.getActivity(
            this,
            0,
            Intent(this, MainActivity::class.java).apply { addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK) },
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, PRIMARY_CHANNEL_ID).apply {
            setContentTitle(title)
            setContentText(text)
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentIntent(notificationPendingIntent)
            setAutoCancel(true)
        }
    }
}