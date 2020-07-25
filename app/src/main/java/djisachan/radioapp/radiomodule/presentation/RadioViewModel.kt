package djisachan.radioapp.radiomodule.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import djisachan.radioapp.radiomodule.data.RadioRepository
import djisachan.radioapp.radiomodule.domain.RadioModel

/**
 * @author Markova Ekaterina on 25-Jul-20
 */
class RadioViewModel(private val radioRepository: RadioRepository) : ViewModel() {

    val radioListData = MutableLiveData<List<RadioModel>>()

    fun uploadRadioList() {
       radioListData.postValue(radioRepository.getRadioList())
    }
}