package com.advay.medicalcongress

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.net.URL

class MyFirebaseMessagingService : FirebaseMessagingService() {

    // Debugging

    override fun onCreate() {
        super.onCreate()
        Log.v("Firebase", "Service Created")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("Firebase", "Service Destroyed...")
    }

    // Override onNewToken to get new token
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("NEW_TOKEN", token)
    }

    private var numMessages = 0

    // Override onMessageReceived() method to extract the
    // title and body from the message passed in FCM
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val notification = remoteMessage.notification
        val data = remoteMessage.data
        Log.d("FROM", remoteMessage.from!!)
        Log.v("Firebase", "Message received!");
        sendNotification(notification, data)
    }

    private fun sendNotification(
        notification: RemoteMessage.Notification?,
        data: Map<String, String>
    ) {
        val bundle = Bundle()
        val channelId = "notification_channel"
        bundle.putString(
            MyFirebaseMessagingService.Companion.FCM_PARAM,
            data[MyFirebaseMessagingService.Companion.FCM_PARAM]
        )
        val intent = Intent(this, navigation::class.java)
        intent.putExtras(bundle)
        val pendingIntent =
            PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notificationBuilder: NotificationCompat.Builder =
            NotificationCompat.Builder(this, channelId)
                .setContentTitle(notification!!.title)
                .setContentText(notification.body)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)) //.setSound(Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.win))
                .setContentIntent(pendingIntent)
                .setContentInfo("Hello")
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setColor(getColor(R.color.black))
                .setLights(Color.RED, 1000, 300)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setNumber(++numMessages)
                .setSmallIcon(R.drawable.logo)
        try {
            val picture = data[MyFirebaseMessagingService.Companion.FCM_PARAM]
            if (picture != null && "" != picture) {
                val url = URL(picture)
                val bigPicture = BitmapFactory.decodeStream(url.openConnection().getInputStream())
                notificationBuilder.setStyle(
                    NotificationCompat.BigPictureStyle().bigPicture(bigPicture).setSummaryText(
                        notification.body
                    )
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                MyFirebaseMessagingService.Companion.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            channel.description = MyFirebaseMessagingService.Companion.CHANNEL_DESC
            channel.setShowBadge(true)
            channel.canShowBadge()
            channel.enableLights(true)
            channel.lightColor = Color.RED
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500)
            assert(notificationManager != null)
            notificationManager.createNotificationChannel(channel)
        }
        assert(notificationManager != null)
        notificationManager.notify(0, notificationBuilder.build())
    }

    companion object {
        const val FCM_PARAM = "picture"
        private const val CHANNEL_NAME = "FCM"
        private const val CHANNEL_DESC = "Firebase Cloud Messaging"
    }

}

