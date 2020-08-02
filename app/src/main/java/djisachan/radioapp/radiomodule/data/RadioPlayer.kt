package djisachan.radioapp.radiomodule.data

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.upstream.AssetDataSource
import com.google.android.exoplayer2.upstream.AssetDataSource.AssetDataSourceException
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DataSpec
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util


/**
 * Плеер проигрывания радиостанции по uri
 *
 * @author Markova Ekaterina on 02-Aug-20
 */
class RadioPlayer(private val context: Context) {

    private var exoPlayer: SimpleExoPlayer = SimpleExoPlayer.Builder(context).build()
    private lateinit var dataSourceFactory: DefaultHttpDataSourceFactory
    private lateinit var mediaSource: MediaSource

    /**
     * Старт проигрывания радио для нового источника
     */
    fun start(uri: String) {
        dataSourceFactory =
            DefaultHttpDataSourceFactory(Util.getUserAgent(context, "djisachan.radio"))

        val dataSpec = DataSpec(Uri.parse(uri))

        //Запуск из assert для отладки
        val assetDataSource = AssetDataSource(context)
        try {
            assetDataSource.open(dataSpec)
        } catch (e: AssetDataSourceException) {
            e.printStackTrace()
        }
        val factory: DataSource.Factory = DataSource.Factory { assetDataSource }
        mediaSource = ExtractorMediaSource(
            assetDataSource.getUri(),
            factory, DefaultExtractorsFactory(), null, null
        )
        //

        exoPlayer.run {
            prepare(mediaSource)
            playWhenReady = true
        }
    }

    /**
     * Рестарт проигрывания уже выбранного радио (без смены источника)
     */
    fun restart() {
        exoPlayer.run {
            prepare(mediaSource)
            playWhenReady = true
        }
    }

    /**
     * Пауза проигрывания
     */
    fun pause() {
        exoPlayer.run {
            playWhenReady = false
        }
    }

}