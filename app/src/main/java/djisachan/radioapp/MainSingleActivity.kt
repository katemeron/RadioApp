package djisachan.radioapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import djisachan.radioapp.radiomodule.domain.RadioModel
import djisachan.radioapp.radiomodule.presentation.RadioPlayCallback
import djisachan.radioapp.radiomodule.presentation.bottomplayer.BottomRadioPlayerFragment
import djisachan.radioapp.radiomodule.presentation.history.HistoryFragment
import djisachan.radioapp.radiomodule.presentation.radiolist.RadioListFragment


/**
 * Главное активити всея приложения
 */
@AndroidEntryPoint
class MainSingleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var radioPlayCallback: RadioPlayCallback
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bottomFragment =
            BottomRadioPlayerFragment()
        radioPlayCallback = bottomFragment
        supportFragmentManager
            .beginTransaction()
            .add(
                R.id.fragment_container,
                RadioListFragment()
            )
            .add(R.id.bottom_container, bottomFragment)
            .commit()
        initToolbar()
        initDrawer()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_sleep -> {
            }
            R.id.action_alarm -> {
            }
            R.id.action_history -> {
                toggle.isDrawerIndicatorEnabled = false
                supportFragmentManager
                    .beginTransaction()
                    .replace(R.id.fragment_container,
                        HistoryFragment()
                    )
                    .addToBackStack(null)
                    .commit()
            }
            R.id.action_settings -> {
            }
            R.id.action_feedback -> {
            }
            else -> {
            }
        }

        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun updateRadioPlayerFragment(radio: RadioModel) {
        radioPlayCallback.updateRadio(radio)
    }

    private fun initToolbar(): Toolbar {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        invalidateOptionsMenu()
        return toolbar
    }

    private fun initDrawer() {
        drawerLayout = findViewById(R.id.main_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        toggle.setToolbarNavigationClickListener {
            initDrawer()
            onBackPressed()
        }
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }
}