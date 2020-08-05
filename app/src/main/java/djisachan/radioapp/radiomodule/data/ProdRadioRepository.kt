package djisachan.radioapp.radiomodule.data

import djisachan.radioapp.radiomodule.domain.RadioModel
import djisachan.radioapp.radiomodule.domain.RadioStationApi
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author Markova Ekaterina on 05-Aug-20
 */
public class ProdRadioRepository() : RadioRepository {

    private val radioStationApi: RadioStationApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        radioStationApi = retrofit.create(RadioStationApi::class.java)
    }

    override fun getRadioList(): Single<List<RadioModel>> {
        return radioStationApi
            .loadRadioStations(COUNTRY)
            .flatMapIterable { response -> response }
            .map { station -> RadioModel(station.name, station.url, station.favicon) }
            .toList()
    }

    companion object {
        private const val BASE_URL = "http://all.api.radio-browser.info/"
        private const val COUNTRY = "korea"
    }

}