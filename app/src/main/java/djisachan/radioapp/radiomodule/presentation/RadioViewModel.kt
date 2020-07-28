package djisachan.radioapp.radiomodule.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import djisachan.radioapp.radiomodule.data.RadioRepository
import djisachan.radioapp.radiomodule.domain.RadioModel
import java.util.*
import kotlin.collections.ArrayList

/**
 * @author Markova Ekaterina on 25-Jul-20
 */
class RadioViewModel(private val radioRepository: RadioRepository) : ViewModel() {

    val radioListData = MutableLiveData<List<RadioModel>>()
    val radioItemsCopy = ArrayList<RadioModel>()

    fun uploadRadioList() {
        radioItemsCopy.addAll(radioRepository.getRadioList())
        radioListData.postValue(radioItemsCopy)
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