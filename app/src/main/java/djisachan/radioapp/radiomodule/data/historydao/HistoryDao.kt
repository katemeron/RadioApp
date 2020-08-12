package djisachan.radioapp.radiomodule.data.historydao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/**
 * Дао истории
 * @author Markova Ekaterina on 09-Aug-20
 */
@Dao
interface HistoryDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(radio: HistoryRadio): Completable

    @Query("SELECT * FROM historyradio")
    fun getAll(): Single<List<HistoryRadio>>

    @Query("SELECT * FROM historyradio WHERE stationuuid = :id")
    fun getRadioById(id: String): Single<HistoryRadio>
}