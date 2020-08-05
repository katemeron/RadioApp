package djisachan.radioapp.radiomodule.presentation

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import djisachan.radioapp.radiomodule.data.RadioRepository
import djisachan.radioapp.radiomodule.domain.RadioModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Markova Ekaterina on 25-Jul-20
 */
class RadioViewModel(private val radioRepository: RadioRepository) : ViewModel() {

    val radioListData = MutableLiveData<List<RadioModel>>()
    val errorData = MutableLiveData<String>()
    val radioItemsCopy = ArrayList<RadioModel>()

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
}