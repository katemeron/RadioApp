package djisachan.radioapp.radiomodule.presentation.bottomplayer

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import djisachan.radioapp.radiomodule.data.historydao.HistoryRadioDatabase
import djisachan.radioapp.radiomodule.data.player.PlayerService
import djisachan.radioapp.radiomodule.domain.RadioModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Вью модель для нижнего плеера
 * @author Markova Ekaterina on 02-Aug-20
 */
class RadioPlayerViewModel @ViewModelInject constructor(private val historyRadioDatabase: HistoryRadioDatabase) :
    ViewModel() {

    private var serviceConnection: ServiceConnection
    private var playerService: PlayerService? = null
    private var isBound = false
    var savedRadioUrl: String? = null

    val lastRadio = MutableLiveData<RadioModel>()

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
        playerService?.stopPlaying()

        val intent = Intent(context, PlayerService::class.java)
        intent.putExtra(PlayerService.URI_KEY, uri)
        context.startService(intent)
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    /**
     * Рестарт проигрывателя на основании прошлой ссылки после паузы
     */
    fun restartPlaying(context: Context) {
        if (isBound) {
            val intent = Intent(context, PlayerService::class.java)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        } else {
            val intent = Intent(context, PlayerService::class.java)
            intent.putExtra(PlayerService.URI_KEY, savedRadioUrl)
            context.startService(intent)
            context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
        }
    }

    /**
     * Пауза проигрывания
     */
    fun pausePlaying(context: Context) {
        context.unbindService(serviceConnection)
    }

    fun getLastRadio(id: String) {
        val dao = historyRadioDatabase.getHistoryDao()
        dao.getRadioById(id)
            .map { history ->
                RadioModel(history.stationuuid, history.name, history.url, history.imageUrl)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ radio -> lastRadio.value = radio }, {})
    }
}