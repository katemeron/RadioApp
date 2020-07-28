package djisachan.radioapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import djisachan.radioapp.radiomodule.presentation.RadioListFragment


/**
 * Главное активити всея приложения
 */
class MainSingleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, RadioListFragment())
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
            R.id.action_settings -> {
            }
            R.id.action_feedback -> {
            }
            else -> {
            }
        }
        val drawer = findViewById<DrawerLayout>(R.id.main_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true
    }

    private fun initToolbar(): Toolbar {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        invalidateOptionsMenu()
        return toolbar
    }

    private fun initDrawer() {
        val drawerLayout = findViewById<DrawerLayout>(R.id.main_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navigationView.setNavigationItemSelectedListener(this)
    }
}