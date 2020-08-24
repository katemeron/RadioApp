package djisachan.radioapp.radiomodule.presentation.radiolist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.api.load
import djisachan.radioapp.R
import djisachan.radioapp.radiomodule.domain.RadioModel
import djisachan.radioapp.radiomodule.presentation.OnRadioClickListener

/**
 * Адаптер списка радиостанций
 * @author Markova Ekaterina on 25-Jul-20
 */
class RadioListAdapter(private val radioClickListener: OnRadioClickListener) :
    RecyclerView.Adapter<RadioListAdapter.RadioViewHolder>() {

    private var radioList: List<RadioModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RadioViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.radio_list_item, parent, false)
        return RadioViewHolder(
            view
        ).apply {
        }
    }

    override fun onBindViewHolder(holder: RadioViewHolder, position: Int) {
        holder.bind(radioList[position])
        holder.itemView.setOnClickListener { radioClickListener.onClick(radioList[position]) }
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
        private val iconView = itemView.findViewById<ImageView>(R.id.radio_icon_view)

        fun bind(radioModel: RadioModel) {
            val circularProgressDrawable = CircularProgressDrawable(iconView.context).apply {
                strokeWidth = 5f
                centerRadius = 30f
                start()
            }
            nameTextView.text = radioModel.name
            if (radioModel.imageUrl.isEmpty()) {
                iconView.setImageResource(R.drawable.ic_baseline_emoji_emotions_24)
            } else {
                iconView.load(radioModel.imageUrl) {
                    crossfade(true)
                    placeholder(circularProgressDrawable)
                    error(R.drawable.ic_baseline_emoji_emotions_24)
                }
            }
        }
    }
}