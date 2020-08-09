package djisachan.radioapp.radiomodule.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import djisachan.radioapp.radiomodule.data.historydao.HistoryRadioDatabase
import djisachan.radioapp.radiomodule.domain.RadioModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Вьюмодель для истории радиостанций
 * @author Markova Ekaterina on 10.08.2020
 */
class HistoryViewModel(
    private val historyRadioDatabase: HistoryRadioDatabase
) : ViewModel() {

    val radioListData = MutableLiveData<List<RadioModel>>()
    val errorData = MutableLiveData<String>()

    /**
     * Получить список истории
     */
    fun getHistoryList() {
        val dao = historyRadioDatabase.getHistoryDao()
        dao.getAll()
            .map {
                it.map { station ->
                    RadioModel(
                        station.stationuuid,
                        station.name,
                        station.url,
                        station.imageUrl
                    )
                }
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ radioListData.value = it }, {})

    }
}