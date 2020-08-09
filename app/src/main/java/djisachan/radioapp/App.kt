package djisachan.radioapp

import android.app.Application
import androidx.room.Room
import djisachan.radioapp.radiomodule.data.historydao.HistoryRadioDatabase


/**
 * @author Markova Ekaterina on 09-Aug-20
 */
class App : Application() {

    lateinit var historyRadioDatabase: HistoryRadioDatabase
        private set

    override fun onCreate() {
        super.onCreate()
        instance = this
        historyRadioDatabase = Room.databaseBuilder(
            applicationContext,
            HistoryRadioDatabase::class.java,
            "history_database"
        ).build()
    }

    companion object {
        lateinit var instance: App
            private set
    }
}