package djisachan.radioapp.radiomodule.data

import djisachan.radioapp.radiomodule.domain.RadioModel

/**
 * Репозиторий работы с радиостанциями для демо-режима
 * @author Markova Ekaterina on 25-Jul-20
 */
class DemoRadioRepository : RadioRepository {
    override fun getRadioList(): List<RadioModel> {
        return listOf(
                RadioModel("Radio 1"),
                RadioModel("Radio 2"),
                RadioModel("Radio 3"),
                RadioModel("Radio 4"),
                RadioModel("Radio 5"),
                RadioModel("Radio 6"),
                RadioModel("Radio 7"),
                RadioModel("Radio 8"),
                RadioModel("Radio 1"),
                RadioModel("Radio 2"),
                RadioModel("Radio 3"),
                RadioModel("Radio 4"),
                RadioModel("Radio 5"),
                RadioModel("Radio 6"),
                RadioModel("Radio 7"),
                RadioModel("Radio 8"))
    }

}