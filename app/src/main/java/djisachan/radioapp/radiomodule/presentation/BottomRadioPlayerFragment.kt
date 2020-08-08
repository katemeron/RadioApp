package djisachan.radioapp.radiomodule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import djisachan.radioapp.R

/**
 * @author Markova Ekaterina on 02-Aug-20
 */
class BottomRadioPlayerFragment : Fragment(), RadioPlayCallback {

    private lateinit var playPauseImageView: ImageView
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
        playPauseImageView = view.findViewById(R.id.play_image_view)
        playPauseImageView.setImageResource(R.drawable.ic_pause_circle_outline_36)//delete
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

    private fun initDependencies() {
        radioPlayerViewModel = RadioPlayerViewModel() //пока без адекватного внедрения зависимостей
    }

    override fun start(url: String) {
        radioPlayerViewModel.startPlaying(requireActivity(), url)
    }
}