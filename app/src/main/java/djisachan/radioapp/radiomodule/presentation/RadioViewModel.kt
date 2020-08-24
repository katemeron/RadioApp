package djisachan.radioapp.radiomodule.presentation

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import djisachan.radioapp.radiomodule.data.RadioRepository
import djisachan.radioapp.radiomodule.data.historydao.HistoryRadio
import djisachan.radioapp.radiomodule.data.historydao.HistoryRadioDatabase
import djisachan.radioapp.radiomodule.domain.RadioModel
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Markova Ekaterina on 25-Jul-20
 */
class RadioViewModel @ViewModelInject constructor(
    private val radioRepository: RadioRepository,
    private val historyRadioDatabase: HistoryRadioDatabase
) : ViewModel() {

    val radioListData = MutableLiveData<List<RadioModel>>()
    val errorData = MutableLiveData<String>()
    val radioItemsCopy = ArrayList<RadioModel>()

    /**
     * Загрузить список радиостанций
     */
    fun uploadRadioList() {
        radioRepository.getRadioList()
            .subscribeOn(Schedulers.io())
            .subscribe({
                radioItemsCopy.addAll(it)
                radioListData.postValue(radioItemsCopy)
            }, {
                errorData.postValue(it.message)
                Log.e("TAG", it.localizedMessage, it)
            })

    }

    /**
     * Поиск радиостанции по строке
     */
    fun queryList(predicate: String) {
        if (predicate.isEmpty()) {
            radioListData.postValue(radioItemsCopy)
        } else {
            radioListData.postValue(radioItemsCopy.filter {
                it.name.toLowerCase(Locale.getDefault())
                    .contains(predicate.toLowerCase(Locale.getDefault()))
            })
        }
    }

    /**
     * Сохранить радиостанцию в историю
     * @param radio модель данных
     */
    fun saveRadioStationToHistory(radio: RadioModel) {
        val dao = historyRadioDatabase.getHistoryDao()
        dao.insert(HistoryRadio(radio.stationuuid, radio.name, radio.url, radio.imageUrl))
            .subscribeOn(Schedulers.io())
            .subscribe()
    }
}