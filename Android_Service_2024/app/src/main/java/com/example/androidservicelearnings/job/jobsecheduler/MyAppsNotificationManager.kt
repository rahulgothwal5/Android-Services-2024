package com.example.androidservicelearnings.job.jobsecheduler

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.androidservicelearnings.R


internal class MyAppsNotificationManager private constructor(private val context: Context) {
    private val notificationManagerCompat: NotificationManagerCompat = NotificationManagerCompat.from(context)
    private val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    fun registerNotificationChannelChannel(
        channelId: String?,
        channelName: String?,
        channelDescription: String?
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            notificationChannel.description = channelDescription
            val notificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    fun getNotification(
        targetNotificationActivity: Class<*>?,
        title: String?,
        priority: Int,
        autoCancel: Boolean,
        notificationId: Int
    ): Notification {
        val intent = Intent(context, targetNotificationActivity)
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        val builder = NotificationCompat.Builder(context, "MY_CUSTOM_CHANNEL")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.drawable.ic_launcher_foreground
                )
            )
            .setContentTitle(title)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setChannelId("123")
            .setAutoCancel(true)
        return builder.build()
    }

    fun cancelNotification(notificationId: Int) {
        notificationManager.cancel(notificationId)
    }

    companion object {
        private var instance: MyAppsNotificationManager? = null
        fun getInstance(context: Context): MyAppsNotificationManager? {
            if (instance == null) {
                instance = MyAppsNotificationManager(context)
            }
            return instance
        }
    }
}