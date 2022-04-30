package com.ernestgm.myposition.ui.splash

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.ernestgm.myposition.R
import com.ernestgm.myposition.configuration.PreferenceManager
import com.ernestgm.myposition.databinding.ActivitySplashBinding
import com.ernestgm.myposition.repository.Repository
import com.ernestgm.myposition.repository.models.Device
import com.ernestgm.myposition.ui.main.MainActivity
import com.ernestgm.myposition.ui.tracking.TrackingState
import com.ernestgm.myposition.ui.tracking.TrackingViewModel
import com.ernestgm.myposition.utils.UIHelper
import com.ernestgm.myposition.utils.goToActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationTokenSource





class SplashActivity : AppCompatActivity() {

    private var _binding:ActivitySplashBinding? = null
    private lateinit var trackingViewModel: TrackingViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val cts = CancellationTokenSource()
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        trackingViewModel = ViewModelProvider(this).get(TrackingViewModel::class.java)

        initView()
        initObservables()
        saveCurrentPositionDevice()
        //gotoNextActivity()
    }

    private fun initView() {
        binding.retryBtn.visibility = View.GONE
    }

    private fun saveCurrentPositionDevice() {
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            UIHelper.showDialog(
                this,
                R.string.permission,
                "You need to allow access to Position",
                R.string.ok
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION

                    ), 100
                )
            }
            return
        }



        fusedLocationClient.getCurrentLocation(LocationRequest.PRIORITY_HIGH_ACCURACY, cts.token)
            .addOnSuccessListener { location : Location? ->
                Repository.local.setDevice(
                    Device(
                        Settings.Secure.getString(this?.contentResolver,
                            Settings.Secure.ANDROID_ID),
                        Build.MODEL,
                        location?.latitude ?: 0.0,
                        location?.longitude ?: 0.0
                    )
                )

                initializeTracking()
            }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        saveCurrentPositionDevice()
    }

    private fun initializeTracking() {
        if (PreferenceManager.get(this).getTrackingEnabled()) {
            trackingViewModel.addDevice(Repository.local.getDevice())
        } else {
            trackingViewModel.removeDevice(Repository.local.getDevice())
        }
    }


    private fun initObservables() {
        trackingViewModel.requestData.observe(this) {
            when (it) {
                is TrackingState.SplashData -> {
                    gotoNextActivity()
                }
                is TrackingState.Loading -> {
                    binding.loader.visibility = if (it.loading) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
                else -> {
                    binding.retryBtn.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun gotoNextActivity() {
        goToActivity<MainActivity>()
        finish()
    }

    override fun onStop() {
        super.onStop()
        cts.cancel()
    }
}