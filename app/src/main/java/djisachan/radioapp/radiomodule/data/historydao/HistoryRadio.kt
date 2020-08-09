package djisachan.radioapp.radiomodule.data.historydao

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Сущность сохранения радиостанции в базу для истории
 * @author Markova Ekaterina on 09-Aug-20
 */
@Entity
data class HistoryRadio(
    @PrimaryKey
    var stationuuid: String,
    var name: String,
    var url: String?,
    var imageUrl: String
)