package djisachan.radioapp.radiomodule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import djisachan.radioapp.R
import djisachan.radioapp.radiomodule.data.DemoRadioRepository

/**
 * @author Markova Ekaterina on 25-Jul-20
 */
class RadioListFragment : Fragment() {

    private lateinit var radioViewModel: RadioViewModel
    private val radioListAdapter = RadioListAdapter()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_radio_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view as RecyclerView
        recyclerView.adapter = radioListAdapter
        radioViewModel = RadioViewModel(DemoRadioRepository())
        //ViewModelProviders.of(this).get(RadioViewModel::class.java)
        radioViewModel.radioListData.observe(viewLifecycleOwner, Observer {
            radioListAdapter.setRadioListData(it)
        })
        radioViewModel.uploadRadioList()
    }
}