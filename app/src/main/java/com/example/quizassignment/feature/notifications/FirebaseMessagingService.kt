package com.example.quizassignment.feature.notifications

import android.app.Notification
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.remoteMessage

class NotifcationMessagingService : FirebaseMessagingService()
{

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        Log.d("notifcation", "Message received from: ${message.from}")
        Log.d("notifcation", "Data payload: ${message.data}")

        val title = message.notification?.title?:message.data["title"]?:""
        val message = message.notification?.body ?: message.data["message"] ?: message.data["body"] ?: return

        NotificationHelper(applicationContext).showNotifcation(title,message)
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("fcm", "New FCM token: $token")

    }

}