package com.danielblandford.spaceflightnews.ui.articles

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import com.danielblandford.spaceflightnews.databinding.FragmentArticlesBinding
import com.danielblandford.spaceflightnews.databinding.ItemArticlesBinding
import com.danielblandford.spaceflightnews.utils.LoadingState
import com.google.android.material.snackbar.Snackbar
import org.koin.android.viewmodel.ext.android.viewModel

class ArticlesFragment : Fragment() {
    private val viewModel by viewModel<ArticlesViewModel>()
    private var _binding: FragmentArticlesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView = binding.recyclerviewArticles
        val adapter = ArticlesAdapter()
        recyclerView.adapter = adapter

        createObservers(adapter)

        viewModel.getArticles()

        return root
    }

    private fun createObservers(adapter: ArticlesAdapter) {
        viewModel.articlesResults.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        //Load state observer
        viewModel.loadingState.observe(viewLifecycleOwner) {
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
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class ArticlesViewHolder(binding: ItemArticlesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val cardView: CardView = binding.itemArticleLayoutParent
        val detailView: ConstraintLayout = binding.itemArticleLayoutDetail
        val expandDetailsImageButton: ImageView = binding.itemArticleExpandDetail
        val articleIV: ImageView = binding.imageViewItemArticleImage
        val titleTV: TextView = binding.textViewItemArticleTitle
        val newsSiteTV: TextView = binding.textViewItemArticleNewsSite
        val summaryTV: TextView = binding.textViewItemArticleSummary
    }
}