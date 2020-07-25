package djisachan.radioapp.radiomodule.presentation

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
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
        radioViewModel = RadioViewModel(DemoRadioRepository())
        radioViewModel.radioListData.observe(viewLifecycleOwner, Observer {
            radioListAdapter.setRadioListData(it)
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
            R.id.action_share -> true
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
}