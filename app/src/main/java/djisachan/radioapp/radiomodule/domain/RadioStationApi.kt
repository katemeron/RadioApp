package djisachan.radioapp.radiomodule.domain

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * @author Markova Ekaterina on 05-Aug-20
 */
interface RadioStationApi {
    @GET("json/stations/bycountry/{searchterm}")
    fun loadRadioStations(@Path("searchterm") country: String): Observable<List<RadioStationResponse>>
}