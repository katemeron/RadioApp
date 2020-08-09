package djisachan.radioapp.radiomodule.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import djisachan.radioapp.App
import djisachan.radioapp.R
import djisachan.radioapp.radiomodule.domain.RadioModel

/**
 * Фрагмент истории выбранных радиостанций
 * @author Markova Ekaterina on 10.08.2020
 */
class HistoryFragment : Fragment(), OnRadioClickListener {

    private val radioListAdapter = RadioListAdapter(this)
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_radio_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = radioListAdapter
        historyViewModel = HistoryViewModel(App.instance.historyRadioDatabase)
        historyViewModel.radioListData.observe(viewLifecycleOwner, Observer {
            radioListAdapter.setRadioListData(it)
        })
        historyViewModel.errorData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        historyViewModel.getHistoryList()
    }

    override fun onClick(radio: RadioModel) {
        //
    }

    private fun initToolbar() {
        with(requireActivity() as AppCompatActivity) {
            title = "История"
            setHasOptionsMenu(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }
}