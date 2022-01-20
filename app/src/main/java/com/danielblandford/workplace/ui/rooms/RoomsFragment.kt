package com.danielblandford.workplace.ui.rooms

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.danielblandford.workplace.databinding.FragmentRoomBinding
import com.danielblandford.workplace.databinding.ItemRoomBinding
import org.koin.android.viewmodel.ext.android.viewModel

class RoomsFragment : Fragment() {
    private val viewModel by viewModel<RoomsViewModel>()
    private var _binding: FragmentRoomBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerviewRoom
        val adapter = RoomAdapter()
        recyclerView.adapter = adapter

        //Rooms observer
        viewModel.roomsResponseList.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })
        viewModel.getRooms()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class RoomViewHolder(binding: ItemRoomBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val imageView: ImageView = binding.imageViewItemRoom
        val roomNameTV: TextView = binding.textViewItemRoomName
        val occupancyTV: TextView = binding.textViewItemRoomOccupancy
    }
}