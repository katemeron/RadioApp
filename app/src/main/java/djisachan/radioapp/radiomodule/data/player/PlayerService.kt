package djisachan.radioapp.radiomodule.data.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Binder
import android.os.IBinder
import android.widget.RemoteViews
import djisachan.radioapp.R


/**
 * Сервис подключения к радиостанции и проигрывания радиостанции
 *
 * @author Markova Ekaterina on 02-Aug-20
 */
class PlayerService : Service() {

    private val playerBinder = PlayerBinder()
    private lateinit var radioPlayer: RadioPlayer
    private var messageId = 1000

    override fun onBind(intent: Intent?): IBinder? {
        return playerBinder
    }

    override fun onRebind(intent: Intent?) {
        radioPlayer.restart()
    }

    override fun onUnbind(intent: Intent?): Boolean {
        radioPlayer.pause()
        return true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val views = RemoteViews(
            packageName,
            R.layout.notification_layout
        )
        val channelId = createNotificationChannel("my_service", "My Background Service")
        val notification = Notification.Builder(this, channelId)
            .setOngoing(true)
            .setContentTitle(intent?.getStringExtra(RADIO_NAME))
            .setCustomContentView(views)
            .build()
        radioPlayer = RadioPlayer(this.applicationContext)
        intent?.getStringExtra(URI_KEY)?.let {
            radioPlayer.start(it)
        }
        startForeground(101, notification)
        return START_STICKY
    }

    fun stopPlaying() {
        radioPlayer.pause()
    }

    private fun createNotificationChannel(channelId: String, channelName: String): String {
        val chan = NotificationChannel(
            channelId,
            channelName, NotificationManager.IMPORTANCE_NONE
        )
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    inner class PlayerBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }

    companion object {
        const val URI_KEY = "djisachan.radio.uri-key"
        const val RADIO_NAME = "djisachan.radio.name-key"
    }
}