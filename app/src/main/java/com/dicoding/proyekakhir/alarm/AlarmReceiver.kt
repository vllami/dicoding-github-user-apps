package com.dicoding.proyekakhir.alarm

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.dicoding.proyekakhir.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class AlarmReceiver : BroadcastReceiver() {

    companion object {
        const val TIME_FORMAT = "HH:mm"
        const val NOTIF_ID = 1
        const val EXTRA_MESSAGE = "extra_message"
        const val EXTRA_TYPE = "extra_type"

        private const val PACKAGE_NAME = "com.dicoding.proyekakhir"
        private const val CHANNEL_ID = "villa"
        private const val CHANNEL_NAME = "GitHub Find"
        private const val REPEAT_ID = 100
    }

    override fun onReceive(context: Context, intent: Intent) {
        val mIntent = context.packageManager.getLaunchIntentForPackage(PACKAGE_NAME)
        val pendingIntent = PendingIntent.getActivity(context, 0, mIntent, 0)
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val mBuilder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentIntent(pendingIntent)
            .setSmallIcon(R.drawable.ic_github_white)
            .setContentTitle(context.getString(R.string.notif_title))
            .setContentText(context.getString(R.string.notif_message))
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val mChannel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(mChannel)

            mBuilder.setChannelId(CHANNEL_ID)
        }
        notificationManager.notify(NOTIF_ID, mBuilder.build())
    }

    private fun String.invalidTimeFormat(time: String): Boolean {
        return try {
            val times = SimpleDateFormat(this, Locale.getDefault())
            with(times) {
                isLenient = false
                parse(time)
            }
            false
        } catch (e: ParseException) {
            true
        }
    }

    fun setOnRepeatingAlarm(context: Context, type: String, time: String, message: String) {
        if (TIME_FORMAT.invalidTimeFormat(time)) return
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, AlarmReceiver::class.java)
        with(intent) {
            putExtra(EXTRA_MESSAGE, message)
            putExtra(EXTRA_TYPE, type)
        }

        val timeFormat = time.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val calendar = Calendar.getInstance()
        with(calendar) {
            set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeFormat[0]))
            set(Calendar.MINUTE, Integer.parseInt(timeFormat[1]))
            set(Calendar.SECOND, 0)
        }

        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_ID, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    fun setOffRepeatingAlarm(context: Context) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, AlarmManager::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, REPEAT_ID, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }

}