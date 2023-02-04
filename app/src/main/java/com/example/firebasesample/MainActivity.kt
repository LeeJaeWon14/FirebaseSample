package com.example.firebasesample

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Color
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
                (it as Button).run {
                    isEnabled = !isEnabled
                    btnStopService.isEnabled = !isEnabled
                }
                NotifyHelper.notify(this@MainActivity, getString(R.string.app_name), "start service..")
            }
            btnStopService.setOnClickListener {
                stopService(Intent(this@MainActivity, MyService::class.java))

                btnStartService.run { isEnabled = !isEnabled }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        stopService(Intent(this@MainActivity, MyService::class.java))
        Toast.makeText(this, "App is destroyed", Toast.LENGTH_SHORT).show()
    }
}