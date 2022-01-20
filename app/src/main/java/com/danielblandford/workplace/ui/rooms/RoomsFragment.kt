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
import com.danielblandford.workplace.utils.LoadingState
import com.google.android.material.snackbar.Snackbar
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

        createObservers(adapter)

        viewModel.getRooms()

        return root
    }

    private fun createObservers(adapter: RoomAdapter) {
        //Rooms observer
        viewModel.roomResults.observe(viewLifecycleOwner, {
            adapter.submitList(it)
        })

        //Load state observer
        viewModel.loadingState.observe(viewLifecycleOwner, {
            when (it.status) {
                LoadingState.Status.FAILED -> {
                    view?.let { it2 ->
                        Snackbar.make(
                            it2,
                            "Error in loading data...check connection",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
                LoadingState.Status.RUNNING -> {
                }
                LoadingState.Status.SUCCESS -> {
                }
            }
        })
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