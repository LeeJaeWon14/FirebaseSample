package com.example.firebasesample

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

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