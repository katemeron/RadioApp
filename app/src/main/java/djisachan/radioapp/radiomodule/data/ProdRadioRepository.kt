package djisachan.radioapp.radiomodule.data

import djisachan.radioapp.radiomodule.domain.RadioModel
import djisachan.radioapp.radiomodule.domain.RadioStationApi
import io.reactivex.Observable

/**
 * @author Markova Ekaterina on 05-Aug-20
 */
class ProdRadioRepository(private val radioStationApi: RadioStationApi) : RadioRepository {

    override fun getRadioList(): Observable<List<RadioModel>> {
        return radioStationApi
            .loadRadioStations(COUNTRY)
            .map {
                it.map { station ->
                    RadioModel(
                        station.stationuuid,
                        station.name,
                        station.url,
                        station.favicon
                    )
                }
            }
    }

    companion object {
        private const val COUNTRY = "korea"
    }

}