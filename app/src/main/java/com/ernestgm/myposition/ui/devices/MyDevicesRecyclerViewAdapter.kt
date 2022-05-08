package com.ernestgm.myposition.ui.devices

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.ernestgm.myposition.databinding.FragmentItemBinding
import com.ernestgm.myposition.repository.models.Device


class MyDevicesRecyclerViewAdapter(val listener: SelectDeviceHandler) : RecyclerView.Adapter<MyDevicesRecyclerViewAdapter.ViewHolder>() {

    private var values: List<Device> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            FragmentItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = values[position]
        holder.contentView.text = item.name
        holder.btnTrackDevice.setOnClickListener {
            listener.onClickItem(item.id)
        }
    }

    fun setValues(list: List<Device>) {
        values = list
        this.notifyDataSetChanged()
    }

    override fun getItemCount(): Int = values.size

    inner class ViewHolder(binding: FragmentItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val contentView: TextView = binding.content
        val btnTrackDevice: Button = binding.btnTrackDevice

        override fun toString(): String {
            return super.toString() + " '" + contentView.text + "'"
        }
    }

}