package djisachan.radioapp.radiomodule.data

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

/**
 * Сервис подключения к радиостанции и проигрывания радиостанции
 *
 * @author Markova Ekaterina on 02-Aug-20
 */
class PlayerService : Service() {

    private val playerBinder = PlayerBinder()
    private lateinit var radioPlayer: RadioPlayer

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
        radioPlayer = RadioPlayer(this.applicationContext)
        intent?.getStringExtra(URI_KEY)?.let {
            radioPlayer.start(it)
        }
        return START_STICKY
    }

    inner class PlayerBinder : Binder() {
        fun getService(): PlayerService {
            return this@PlayerService
        }
    }

    companion object {
        const val URI_KEY = "djisachan.radio.uri-key"
    }
}