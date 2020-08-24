package djisachan.radioapp.radiomodule.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import djisachan.radioapp.radiomodule.data.ProdRadioRepository
import djisachan.radioapp.radiomodule.data.RadioRepository
import djisachan.radioapp.radiomodule.data.historydao.HistoryRadioDatabase
import djisachan.radioapp.radiomodule.domain.RadioStationApi
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * @author Markova Ekaterina on 24.08.2020
 */
@Module
@InstallIn(ApplicationComponent::class)
object RadioInnerModule {

    @Provides
    @Singleton
    fun provideHistoryRadioDatabase(@ApplicationContext context: Context): HistoryRadioDatabase {
        return Room.databaseBuilder(
            context,
            HistoryRadioDatabase::class.java,
            "history_database"
        ).build()
    }

    @Provides
    fun providesRadioRepository(radioStationApi: RadioStationApi): RadioRepository {
        return ProdRadioRepository(radioStationApi)
    }

    @Provides
    @Singleton
    fun providesRadioStationApi(): RadioStationApi {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(RadioStationApi::class.java)
    }

    private const val BASE_URL = "http://all.api.radio-browser.info/"
}