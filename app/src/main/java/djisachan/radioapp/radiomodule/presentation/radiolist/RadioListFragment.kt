package djisachan.radioapp.radiomodule.presentation.radiolist

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import djisachan.radioapp.MainSingleActivity
import djisachan.radioapp.R
import djisachan.radioapp.radiomodule.domain.RadioModel
import djisachan.radioapp.radiomodule.presentation.OnRadioClickListener

/**
 * @author Markova Ekaterina on 25-Jul-20
 */
@AndroidEntryPoint
class RadioListFragment : Fragment(),
    OnRadioClickListener {

    private val radioViewModel: RadioViewModel by viewModels()
    private val radioListAdapter =
        RadioListAdapter(
            this
        )

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
        radioViewModel.radioListData.observe(viewLifecycleOwner, Observer {
            radioListAdapter.setRadioListData(it)
        })
        radioViewModel.errorData.observe(viewLifecycleOwner, Observer {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
        })
        radioViewModel.uploadRadioList()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.radio_menu, menu)
        val searchText = menu.findItem(R.id.action_search).actionView as SearchView
        searchText.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                radioViewModel.queryList(query)
                return true
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> true
            R.id.action_exit -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun initToolbar() {
        with(requireActivity() as AppCompatActivity) {
            title = "Радиостанции"
            setHasOptionsMenu(true)
        }
    }

    override fun onClick(radio: RadioModel) {
        (requireActivity() as MainSingleActivity).updateRadioPlayerFragment(radio)
        radioViewModel.saveRadioStationToHistory(radio)
    }
}