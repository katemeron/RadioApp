package djisachan.radioapp

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.TextView
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
class MainSingleActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    SensorEventListener {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var sensorManager: SensorManager
    private lateinit var tempViewGroup: View
    private lateinit var pressureViewGroup: View
    private lateinit var tempTextView: TextView
    private lateinit var pressureTextView: TextView
    private var temperature: Sensor? = null
    private var pressure: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, RadioListFragment())
            .commit()
        initToolbar()
        initViews()
        initSensors()
    }

    override fun onResume() {
        super.onResume()
        temperature?.let {
            sensorManager.registerListener(this, temperature, SensorManager.SENSOR_DELAY_NORMAL)
        }
        pressure?.let {
            sensorManager.registerListener(this, pressure, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
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
        drawerLayout.closeDrawer(GravityCompat.START);
        return true
    }

    private fun initToolbar(): Toolbar {
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        invalidateOptionsMenu()
        return toolbar
    }

    private fun initViews() {
        drawerLayout = findViewById(R.id.main_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)
        val headerView = navigationView.inflateHeaderView(R.layout.drawer_header_layout)
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

        tempViewGroup = headerView.findViewById(R.id.temperature_group)
        pressureViewGroup = headerView.findViewById(R.id.pressure_group)
        tempTextView = headerView.findViewById(R.id.temperature_sensor)
        pressureTextView = headerView.findViewById(R.id.sensor2)
    }


    private fun initSensors() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        temperature = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        tempViewGroup.visibility = if (temperature == null) View.GONE else View.VISIBLE
        pressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)
        pressureViewGroup.visibility = if (pressure == null) View.GONE else View.VISIBLE
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        //not used
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            when (it.sensor?.type) {
                Sensor.TYPE_AMBIENT_TEMPERATURE -> updateTemp(event.values[0].toString())
                Sensor.TYPE_PRESSURE -> updatePressure(event.values[0].toString())
                else -> {
                }
            }
        }
    }

    fun updateTemp(temp: String) {
        tempTextView.text = temp
    }

    fun updatePressure(pressure: String) {
        pressureTextView.text = pressure
    }

}