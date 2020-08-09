package djisachan.radioapp.radiomodule.presentation

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.api.load
import djisachan.radioapp.App
import djisachan.radioapp.R
import djisachan.radioapp.radiomodule.domain.RadioModel

/**
 * @author Markova Ekaterina on 02-Aug-20
 */
class BottomRadioPlayerFragment : Fragment(), RadioPlayCallback {

    private lateinit var lastRadioSharedPreferences: SharedPreferences
    private lateinit var playPauseImageView: ImageView
    private lateinit var radioIcon: ImageView
    private lateinit var radioName: TextView
    private var isPlaying: Boolean = true

    private lateinit var radioPlayerViewModel: RadioPlayerViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_player_layout, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initDependencies()
        initView(view)
        initLastRadio()
    }

    override fun updateRadio(radio: RadioModel) {
        start(radio)
        updateSharedPref(radio)
    }

    private fun initLastRadio() {
        lastRadioSharedPreferences = requireActivity().getSharedPreferences(LAST_RADIO_PREF, MODE_PRIVATE)
        val lastId = lastRadioSharedPreferences.getString(LAST_RADIO_ID, null)
        lastId?.let { radioPlayerViewModel.getLastRadio(it) }
    }

    private fun initDependencies() {
        radioPlayerViewModel = RadioPlayerViewModel(App.instance.historyRadioDatabase) //пока без адекватного внедрения зависимостей
        radioPlayerViewModel.lastRadio.observe(viewLifecycleOwner, Observer {
            start(it)
        })
    }

    private fun initView(view: View) {
        radioName = view.findViewById(R.id.radio_name_view)
        radioIcon = view.findViewById(R.id.radio_icon)
        playPauseImageView = view.findViewById(R.id.play_image_view)
        playPauseImageView.setOnClickListener {
            isPlaying = if (isPlaying) {
                playPauseImageView.setImageResource(R.drawable.ic_play_circle_outline_36)
                radioPlayerViewModel.pausePlaying(requireContext())
                false
            } else {
                playPauseImageView.setImageResource(R.drawable.ic_pause_circle_outline_36)
                radioPlayerViewModel.restartPlaying(requireContext())
                true
            }
        }
    }

    private fun start(radio: RadioModel) {
        radio.url?.let {
            val circularProgressDrawable = CircularProgressDrawable(radioIcon.context).apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }
            radioName.text = radio.name
            radioIcon.load(radio.imageUrl) {
                crossfade(true)
                placeholder(circularProgressDrawable)
                error(R.drawable.ic_baseline_emoji_emotions_24)
            }

            radioPlayerViewModel.startPlaying(requireActivity(), it)
            playPauseImageView.setImageResource(R.drawable.ic_pause_circle_outline_36)
            isPlaying = true
        }
    }

    private fun updateSharedPref(radio: RadioModel) {
        lastRadioSharedPreferences.edit()
                .putString(LAST_RADIO_ID, radio.stationuuid)
                .apply()
    }

    companion object {
        private const val LAST_RADIO_PREF = "last.radio.shared.preference"
        private const val LAST_RADIO_ID = "last.radio.id"
    }
}