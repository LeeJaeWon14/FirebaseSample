package com.example.firebasesample

import android.util.Log
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyMessagingService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "FCM"
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
        isRestricted
        remoteMessage.notification?.let {
            it.title?.let { it1 -> log(it1) }
            it.body?.let { it1 -> log(it1) }
        }
    }

    private fun log(msg: String) = Log.i(TAG, msg)
}