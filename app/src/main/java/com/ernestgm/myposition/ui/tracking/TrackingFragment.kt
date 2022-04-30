package com.ernestgm.myposition.ui.tracking

import android.os.Bundle
import android.provider.Settings
import android.system.Os
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ernestgm.myposition.R
import com.ernestgm.myposition.databinding.FragmentHomeBinding
import com.ernestgm.myposition.repository.Repository
import com.ernestgm.myposition.repository.models.Device
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class TrackingFragment : Fragment(), OnMapReadyCallback {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var mMap: GoogleMap
    private lateinit var homeViewModel: TrackingViewModel

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeViewModel =
            ViewModelProvider(this).get(TrackingViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

//        val textView: TextView = binding.textHome
//        homeViewModel.text.observe(viewLifecycleOwner) {
//            textView.text = it
//        }

        iniView()
        return root
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

        // Add a marker in Sydney and move the camera
        val currentDevice = Repository.local.getDevice()
        val devicePosition = LatLng(currentDevice.latitude, currentDevice.longitude)
        mMap.addMarker(MarkerOptions().position(devicePosition).title("Device" + currentDevice.name))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(devicePosition))

        //homeViewModel.addDevice(Device(android.os.Build.MODEL, sydney.latitude, sydney.longitude))
//        homeViewModel.updateDevice(
//            Device(
//                Settings.Secure.getString(context?.contentResolver,
//                    Settings.Secure.ANDROID_ID),
//                android.os.Build.MODEL,
//                sydney.latitude,
//                sydney.longitude
//            )
//        )
    }
}