package com.example.quizassignment.feature.notifications

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Message
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.quizassignment.R
import kotlin.random.Random

class NotificationHelper(
    val context: Context
) {
    fun showNotifcation(title: String,message: String)
    {
         val notification = NotificationCompat.Builder(
             context,
             NotificationConstants.CHANNEL_ID
         ).setSmallIcon(R.drawable.ic_notification)
             .setContentTitle(title)
             .setContentText(message)
             .setStyle(
                 NotificationCompat.BigTextStyle()
                     .bigText(message)
             )
             .setPriority(NotificationCompat.PRIORITY_HIGH)
             .setAutoCancel(true)
             .build()


        if(ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS)!= PackageManager.PERMISSION_GRANTED)
        {
            return
        }

        NotificationManagerCompat
            .from(context)
            .notify(generateNotificationId(), notification)
    }

    private fun generateNotificationId(): Int {
        return Random.nextInt()
    }
}
