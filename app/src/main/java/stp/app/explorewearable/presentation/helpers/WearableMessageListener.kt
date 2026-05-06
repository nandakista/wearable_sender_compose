package stp.app.explorewearable.presentation.helpers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.android.gms.wearable.MessageEvent
import com.google.android.gms.wearable.WearableListenerService

class WearableMessageListener : WearableListenerService() {
    override fun onMessageReceived(messageEvent: MessageEvent) {
        Log.d("WATCH_APP", "path: ${messageEvent.path}")
        if (messageEvent.path == "/notify") {
            val message = String(messageEvent.data)
            Log.d("WATCH_APP", "message: $message")
            showNotification("From Phone", message)
        }
    }

    private fun showNotification(title: String, message: String) {
        Log.d("WATCH_APP", "SHOWING NOTIFICATION")
        val channelId = "default_channel"

        val manager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        val channel = NotificationChannel(
            channelId,
            "Default Channel",
            NotificationManager.IMPORTANCE_HIGH
        )
        manager.createNotificationChannel(channel)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        manager.notify(1001, notification)
    }
}