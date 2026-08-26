package com.example.quizassignment.app

import android.app.Application
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.example.quizassignment.feature.notifications.NotificationConstants
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class QuizApplication : Application()
{
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return

        val channel = NotificationChannel(
            NotificationConstants.CHANNEL_ID,
            NotificationConstants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = NotificationConstants.CHANNEL_DESCRIPTION
            enableVibration(true)
        }

        getSystemService(NotificationManager::class.java)
            .createNotificationChannel(channel)
    }
}
