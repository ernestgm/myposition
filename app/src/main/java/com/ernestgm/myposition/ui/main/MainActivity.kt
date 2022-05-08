package com.ernestgm.myposition.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.ernestgm.myposition.R
import com.ernestgm.myposition.configuration.PreferenceManager
import com.ernestgm.myposition.databinding.ActivityMainBinding
import com.ernestgm.myposition.eventbus.UpdateLIstDeviceEvent
import com.ernestgm.myposition.repository.Repository
import com.ernestgm.myposition.ui.tracking.TrackingState
import com.ernestgm.myposition.ui.tracking.TrackingViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.greenrobot.eventbus.EventBus


class MainActivity : AppCompatActivity(), IFragmentNavigationHandler {

    private lateinit var binding: ActivityMainBinding
    private lateinit var trackingViewModel: TrackingViewModel
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        trackingViewModel = ViewModelProvider(this).get(TrackingViewModel::class.java)

        val navView: BottomNavigationView = binding.navView

        navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_tracking, R.id.navigation_devices
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        trackingMyLocation()
        initObservables()
        trackingViewModel.getAllDevices()
    }

    private fun trackingMyLocation() {
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val hasGps = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)

        if (hasGps && PreferenceManager.get(this).getTrackingEnabled()) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return
            }
            locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                1000,
                0F,
                object : LocationListener {
                    override fun onLocationChanged(locationGps: Location) {
                        val currentDevice = Repository.local.getDevice()
                        currentDevice.updateLocation(locationGps.latitude, locationGps.longitude)
                        trackingViewModel.updateDevice(currentDevice)
                    }
                })
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        val itemswitch: MenuItem = menu.findItem(R.id.app_bar_switch)
        itemswitch.setActionView(R.layout.switch_item)
        val swEnabledTracking = menu.findItem(R.id.app_bar_switch).getActionView()
            .findViewById(R.id.sw_enable_tracking) as Switch

        swEnabledTracking.isChecked = PreferenceManager.get(this).getTrackingEnabled()

        swEnabledTracking.setOnCheckedChangeListener { buttonView, isChecked ->
            PreferenceManager.get(this).setTrackingEnabled(isChecked)
            if (isChecked) {
                trackingViewModel.addDevice(Repository.local.getDevice())
                Toast.makeText(this@MainActivity, "The tracking is enabled", Toast.LENGTH_SHORT).show()
            } else {
                trackingViewModel.removeDevice(Repository.local.getDevice())
                Toast.makeText(this@MainActivity, "The tracking is disabled", Toast.LENGTH_SHORT).show()
            }
        }
        return true
    }

    private fun initObservables() {
        trackingViewModel.requestData.observe(this) { data ->
            when (data) {
                is TrackingState.DevicesListData -> {
                    Repository.local.setDeviceLIst(data.devices)
                    EventBus.getDefault().postSticky(UpdateLIstDeviceEvent())
                }
            }
        }
    }

    override fun goToFragment(
        actionNavigationId: Int,
        bundle: Bundle?
    ) {
        navController.navigate(actionNavigationId, bundle)
    }
}