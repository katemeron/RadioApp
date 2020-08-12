package djisachan.radioapp.radiomodule.presentation

import djisachan.radioapp.radiomodule.domain.RadioModel

/**
 * Колбэк запуска новой радиостанции для плеера
 * @author Markova Ekaterina on 08.08.2020
 */
interface RadioPlayCallback {

    fun updateRadio(radio: RadioModel)
}