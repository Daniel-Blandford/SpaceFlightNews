package com.danielblandford.workplace.ui.people

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.danielblandford.workplace.databinding.FragmentPeopleBinding
import com.danielblandford.workplace.databinding.ItemPeopleBinding
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.danielblandford.workplace.utils.LoadingState
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class PeopleFragment : Fragment() {
    private val viewModel by viewModel<PeopleViewModel>()
    private var _binding: FragmentPeopleBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPeopleBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerviewPeople
        val adapter = PeopleAdapter()
        recyclerView.adapter = adapter

        createObservers(adapter)

        viewModel.getPeople()

        return root
    }

    private fun createObservers(adapter: PeopleAdapter) {
        //People observer
        viewModel.peopleResults.observe(viewLifecycleOwner, {
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

    class PeopleViewHolder(binding: ItemPeopleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cardView: CardView = binding.itemPeopleLayoutParent
        val detailView: ConstraintLayout = binding.itemPeopleLayoutDetail
        val expandDetailsImageButton: ImageView = binding.itemPeopleExpandDetail
        val avatarIV: ImageView = binding.imageViewItemPeopleAvatar
        val nameTV: TextView = binding.textViewItemPeopleName
        val jobTitleTV: TextView = binding.textViewItemPeopleJobTitle
        val emailTV: TextView = binding.textViewItemPeopleEmail
        val phoneTV: TextView = binding.textViewItemPeoplePhone
        val locationTV: TextView = binding.textViewItemPeopleLocation
    }
}