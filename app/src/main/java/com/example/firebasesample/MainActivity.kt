package com.example.firebasesample

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.firebasesample.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var notificationManager: NotificationManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.apply {
            btnStartService.setOnClickListener {
                startService(Intent(this@MainActivity, MyService::class.java))
                startActivity(Intent(Intent.ACTION_MAIN).apply {
                    addCategory(Intent.CATEGORY_HOME)
                    setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                })
                (it as Button).run { isEnabled = !isEnabled }
            }
            btnStopService.setOnClickListener {
                stopService(Intent(this@MainActivity, MyService::class.java))

                getNotificationBuilder(getString(R.string.app_name), "Stop service")
                btnStartService.run { isEnabled = !isEnabled }
            }
        }
        createNotificationChannel()
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this@MainActivity, MyService::class.java))
        Toast.makeText(this, "App is destroyed", Toast.LENGTH_SHORT).show()
    }

    private fun createNotificationChannel() {
        notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                "ABC",
                "Messaging",
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

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            when(it.action) {
                Intent.ACTION_SHUTDOWN -> { Log.e("TEST", "Shut down") }
                Intent.ACTION_AIRPLANE_MODE_CHANGED -> {
                    Toast.makeText(context, "AIRPLANE MODE", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}