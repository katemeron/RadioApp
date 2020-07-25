package djisachan.radioapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import djisachan.radioapp.radiomodule.presentation.RadioListFragment

/**
 * Главное активити всея приложения
 */
class MainSingleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
                .beginTransaction()
                .add(R.id.main_container, RadioListFragment())
                .commit()
    }
}