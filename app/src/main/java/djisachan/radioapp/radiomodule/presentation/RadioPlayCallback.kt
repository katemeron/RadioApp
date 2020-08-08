package djisachan.radioapp.radiomodule.presentation

/**
 * Колбэк запуска новой радиостанции для плеера
 * @author Markova Ekaterina on 08.08.2020
 */
interface RadioPlayCallback {

    fun start(url: String)
}