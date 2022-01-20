package com.danielblandford.workplace.ui.rooms

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.danielblandford.workplace.R
import com.danielblandford.workplace.databinding.ItemRoomBinding
import com.danielblandford.workplace.ui.rooms.data.Room

class RoomAdapter :
    ListAdapter<Room, RoomsFragment.RoomViewHolder>(object : DiffUtil.ItemCallback<Room>() {

        override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsFragment.RoomViewHolder {
        val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return RoomsFragment.RoomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RoomsFragment.RoomViewHolder, roomNumber: Int) {
        holder.roomNameTV.text = getItem(roomNumber).name
        holder.occupancyTV.text = holder.occupancyTV.context.resources
            .getString(R.string.max_occupancy).format(getItem(roomNumber).max_occupancy)

        holder.imageView.setImageDrawable(
            if(getItem(roomNumber).is_occupied){
                ResourcesCompat.getDrawable(holder.imageView.resources, R.drawable.ic_room_full, null)
            }
            else{
                ResourcesCompat.getDrawable(holder.imageView.resources, R.drawable.ic_room_empty, null)
            }
        )
    }
}