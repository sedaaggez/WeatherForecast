package com.sedaaggez.weatherforecast.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.sedaaggez.weatherforecast.view.MainActivity
import com.sedaaggez.weatherforecast.R


class FirebaseMessagingService : FirebaseMessagingService() {

    var NOTIFICATION_CHANNEL_ID = "com.sedaaggez.weatherforecast"

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        var title = p0?.notification?.title
        var body = p0?.notification?.body
        showNotification(title, body)
    }

    fun showNotification(title: String?, text: String?) {
        var intent = Intent(this, MainActivity::class.java)
        var pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        var soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        var notification = NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(text)
            .setSound(soundUri)
            .setOngoing(true)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        var notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            var channel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                "Customer",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(0, notification.build())
    }


}