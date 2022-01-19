package com.danielblandford.workplace.ui.rooms

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.danielblandford.workplace.R
import com.danielblandford.workplace.databinding.FragmentRoomBinding
import com.danielblandford.workplace.databinding.ItemRoomBinding
import com.danielblandford.workplace.ui.rooms.data.Room

class RoomsFragment : Fragment() {

    private lateinit var roomsViewModel: RoomsViewModel
    private var _binding: FragmentRoomBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        roomsViewModel = ViewModelProvider(this).get(RoomsViewModel::class.java)
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerviewRoom
        val adapter = RoomAdapter()
        recyclerView.adapter = adapter

        //Rooms observer
        roomsViewModel.roomsResponseList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
            for (room in it) {
                Log.d("Room created_at", room.created_at)
                Log.d("Room id", room.id)
                Log.d("Room is_occupied", ""+room.is_occupied)
                Log.d("Room max_occupancy", ""+room.max_occupancy)
                Log.d("Room name", room.name)
            }
        })
        roomsViewModel.getRooms()
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class RoomAdapter :
        ListAdapter<Room, RoomViewHolder>(object : DiffUtil.ItemCallback<Room>() {

            override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean =
                oldItem == newItem
        }) {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomViewHolder {
            val binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent,false)
            return RoomViewHolder(binding)
        }

        override fun onBindViewHolder(holder: RoomViewHolder, roomNumber: Int) {
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

    class RoomViewHolder(binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {

        val imageView: ImageView = binding.imageViewItemRoom
        val roomNameTV: TextView = binding.textViewItemRoomName
        val occupancyTV: TextView = binding.textViewItemRoomOccupancy
    }
}