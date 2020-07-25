package djisachan.radioapp.radiomodule.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import djisachan.radioapp.R
import djisachan.radioapp.radiomodule.domain.RadioModel

/**
 * Адаптер списка радиостанций
 * @author Markova Ekaterina on 25-Jul-20
 */
class RadioListAdapter() : RecyclerView.Adapter<RadioListAdapter.RadioViewHolder>() {

    private var radioList: List<RadioModel> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.radio_list_item, parent, false)
        return RadioViewHolder(view)
    }

    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        holder.bind(radioList[position])
    }

    override fun getItemCount(): Int {
        return radioList.size
    }

    fun setRadioListData(updatedRadioList: List<RadioModel>) {
        radioList = updatedRadioList
        notifyDataSetChanged()
    }

    class RadioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView = itemView.findViewById<TextView>(R.id.radio_name_view)

        fun bind(radioModel: RadioModel) {
            nameTextView.text = radioModel.name
        }
    }
}