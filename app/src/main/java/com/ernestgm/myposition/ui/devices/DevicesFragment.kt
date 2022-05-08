package com.ernestgm.myposition.ui.devices

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.ernestgm.myposition.R
import com.ernestgm.myposition.eventbus.UpdateLIstDeviceEvent
import com.ernestgm.myposition.repository.Repository
import com.ernestgm.myposition.ui.tracking.ARG_TRACKING_DEVICE
import com.ernestgm.myposition.ui.tracking.TrackingViewModel
import com.ernestgm.myposition.utils.navigateTo
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * A fragment representing a list of Items.
 */
class DevicesFragment : Fragment(), SelectDeviceHandler {

    private lateinit var viewModel: TrackingViewModel
    private lateinit var devicesAdapter: MyDevicesRecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_list, container, false)

        // Set the adapter
        devicesAdapter = MyDevicesRecyclerViewAdapter(this)
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = devicesAdapter
            }
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TrackingViewModel::class.java)
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    fun updateList(event: UpdateLIstDeviceEvent) {
        val devices = Repository.local.getDeviceLIst()
        devicesAdapter.setValues(devices)
    }

    override fun onStart() {
        EventBus.getDefault().register(this)
        super.onStart()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    override fun onClickItem(id: String) {
        navigateTo(R.id.navigation_tracking, Bundle().apply {
            putString(ARG_TRACKING_DEVICE, id)
        })
    }
}