package djisachan.radioapp.radiomodule.data.historydao

import androidx.room.Database
import androidx.room.RoomDatabase

/**
 * БД истории
 * @author Markova Ekaterina on 09-Aug-20
 */
@Database(entities = [HistoryRadio::class], version = 1, exportSchema = false)
abstract class HistoryRadioDatabase : RoomDatabase() {
    abstract fun getHistoryDao(): HistoryDao
}
