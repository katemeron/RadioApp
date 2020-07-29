package djisachan.radioapp.utils

import android.app.Dialog
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import djisachan.radioapp.R

/**
 * @author Markova Ekaterina on 29-Jul-20
 */
class DialogWrapperFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        activity?.let {
            val contentView = it.layoutInflater.inflate(R.layout.image_layout, null) as ImageView
            contentView.setImageResource(R.drawable.shotsh)
            val builder = AlertDialog.Builder(it)
                .setTitle("Увы, но пока вы никак не можете связать с нами!")
                .setView(contentView)
                .setPositiveButton("НУ ЧТОЖ") { _, _ -> dismiss() }
            return builder.create()
        } ?: throw IllegalStateException("Activity should not be null")
    }
}