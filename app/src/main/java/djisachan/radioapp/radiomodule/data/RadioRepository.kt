package djisachan.radioapp.radiomodule.data

import djisachan.radioapp.radiomodule.domain.RadioModel

/**
 * Репозиторий работы с радиостанциями
 * @author Markova Ekaterina on 25-Jul-20
 */
interface RadioRepository {

    /**
     * Получение списка радиостанций
     */
    fun getRadioList(): List<RadioModel>

}