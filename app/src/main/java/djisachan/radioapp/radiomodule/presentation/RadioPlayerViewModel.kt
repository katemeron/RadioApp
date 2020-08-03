package djisachan.radioapp.radiomodule.presentation

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.ViewModel
import djisachan.radioapp.radiomodule.data.PlayerService

/**
 * Вью модель для нижнего плеера
 * @author Markova Ekaterina on 02-Aug-20
 */
class RadioPlayerViewModel : ViewModel() {

    private var serviceConnection: ServiceConnection
    private lateinit var playerService: PlayerService
    private var isBound = false

    init {
        serviceConnection = object : ServiceConnection {
            override fun onServiceConnected(
                className: ComponentName,
                service: IBinder
            ) {
                Log.d("LOG_TAG", "MainActivity onServiceConnected");
                val binder = service as PlayerService.PlayerBinder
                playerService = binder.getService()
                isBound = true
            }

            override fun onServiceDisconnected(name: ComponentName) {
                isBound = false
            }
        }
    }

    /**
     * Запуск проигрывателя, если выбрано радио
     */
    fun startPlaying(context: Context, uri: String) {
        val intent = Intent(context, PlayerService::class.java)
        intent.putExtra(PlayerService.URI_KEY, uri)
        context.startService(intent)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * Рестарт проигрывателя на основании прошлой ссылки после паузы
     */
    fun restartPlaying(context: Context) {
        val intent = Intent(context, PlayerService::class.java)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * Пауза проигрывания
     */
    fun pausePlaying(context: Context) {
        context.unbindService(serviceConnection)
    }
}