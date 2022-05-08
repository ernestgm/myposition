package com.ernestgm.myposition.ui.tracking

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ernestgm.myposition.R
import com.ernestgm.myposition.databinding.FragmentTrackingBinding
import com.ernestgm.myposition.repository.Repository
import com.ernestgm.myposition.repository.models.Device
import com.ernestgm.myposition.ui.devices.DevicesFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions


const val ARG_TRACKING_DEVICE = "device_tracking"

class TrackingFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentTrackingBinding? = null
    private lateinit var mMap: GoogleMap
    private lateinit var trakingViewModel: TrackingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var currentTrackingDeviceId: String
    private var currentMarker: Marker? = null
    private var currentDevice: Device = Repository.local.getDevice()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        currentTrackingDeviceId = currentDevice.id
        arguments?.let {
            currentTrackingDeviceId = it.getString(ARG_TRACKING_DEVICE, "")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        trakingViewModel =
            ViewModelProvider(this).get(TrackingViewModel::class.java)

        _binding = FragmentTrackingBinding.inflate(inflater, container, false)
        val root: View = binding.root

        iniView()
        initObservables()
        initTrackingForAll()
        return root
    }

    private fun initTrackingForAll() {
        trakingViewModel.trackingDevices()
    }

    private fun initObservables() {
        trakingViewModel.requestData.observe(viewLifecycleOwner) { data ->
            when (data) {
                is TrackingState.DeviceData -> {
                    updatePosition(data.device)
                }
            }
        }
    }

    private fun updatePosition(device: Device) {
        if (device.id == currentTrackingDeviceId) {
            currentMarker?.position = LatLng(device.latitude, device.longitude)
        }
    }


    private fun iniView() {
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.clear()
        // Add a marker in Sydney and move the camera

        val deviceTracking = Repository.local.getDeviceLIst().find {
            it.id == currentTrackingDeviceId
        } ?: Repository.local.getDevice()

        val devicePosition = LatLng(deviceTracking.latitude, deviceTracking.longitude)
        currentMarker = mMap.addMarker(
            MarkerOptions()
                .position(devicePosition)
                .title("Device " + deviceTracking.name)
        )!!
        mMap.moveCamera(CameraUpdateFactory.newLatLng(devicePosition))

        mMap.setMinZoomPreference(10.0f)
        mMap.setMaxZoomPreference(17.0f)
    }

    companion object {

        // TODO: Customize parameter argument names

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(device: String) =
            DevicesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_TRACKING_DEVICE, device)
                }
            }
    }
}