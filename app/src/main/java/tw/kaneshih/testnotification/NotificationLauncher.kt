package tw.kaneshih.testnotification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

private const val CHANNEL_ID = "notification_channel_default"

class NotificationLauncher(private val context: Context) {

    fun post(title: String, description: String, groupId: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            initChannel()
        }

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(description)
            .setAutoCancel(true)
            .setGroup(groupId)

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(groupId, System.currentTimeMillis().toInt(), builder.build())
        notificationManager.notify(groupId, groupId.hashCode(), createSummaryNotification(groupId))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun initChannel() {
        val mNotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val id = CHANNEL_ID
        val name = context.getString(R.string.notification_channel_name)
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)
        channel.setShowBadge(true)
        channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
        mNotificationManager.createNotificationChannel(channel)
    }

    private fun createSummaryNotification(groupId: String): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("sum:title:$groupId")
            .setContentText("sum:text:$groupId")
            .setStyle(
                NotificationCompat.InboxStyle()
                    .setBigContentTitle("sum:bigTitle:$groupId")
                    .setSummaryText("sum:sumText:$groupId")
            )
            .setGroup(groupId)
            .setGroupSummary(true)
            .setAutoCancel(true)
            .build()
    }
}