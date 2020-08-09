package djisachan.radioapp.radiomodule.data

import djisachan.radioapp.radiomodule.domain.RadioModel
import io.reactivex.Observable

/**
 * Репозиторий работы с радиостанциями
 * @author Markova Ekaterina on 25-Jul-20
 */
interface RadioRepository {

    /**
     * Получение списка радиостанций
     */
    fun getRadioList(): Observable<List<RadioModel>>

}