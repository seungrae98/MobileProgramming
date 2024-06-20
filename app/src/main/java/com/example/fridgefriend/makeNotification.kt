package com.example.fridgefriend

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat

fun makeNotification(context: Context, msg: String, pendingIntent: PendingIntent, notificationId: Int) {
    val channelId = "MyChannel"
    val channelName = "TimeCheckChannel"

    val notificationChannel =
        NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)

    val notificationManager=context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(notificationChannel)

    val notification = NotificationCompat.Builder(context, channelId)
        .setSmallIcon(R.mipmap.ic_launcher)
        .setContentTitle("유통기한 알림")
        .setContentText(msg)
        .setPriority(NotificationManager.IMPORTANCE_HIGH)
        .setContentIntent(pendingIntent)
        .setAutoCancel(true)
        .build()

    notificationManager.notify(notificationId, notification)
}