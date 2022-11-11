package com.danielblandford.spaceflightnews.ui.articles

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.danielblandford.spaceflightnews.R
import com.danielblandford.spaceflightnews.databinding.ItemArticlesBinding
import com.danielblandford.spaceflightnews.ui.articles.data.Article
import com.danielblandford.spaceflightnews.utils.UrlBitmap
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*

class ArticlesAdapter :
    ListAdapter<Article, ArticlesFragment.ArticlesViewHolder>(object : DiffUtil.ItemCallback<Article>() {

        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
            oldItem == newItem
    }) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticlesFragment.ArticlesViewHolder {
        val binding = ItemArticlesBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return ArticlesFragment.ArticlesViewHolder(binding)
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ArticlesFragment.ArticlesViewHolder, article: Int) {
        //Download avatar and change its shape from square to circle
        UrlBitmap.urlToBitmapCircle(getItem(article).imageUrl, holder.articleIV)

        holder.titleTV.text = getItem(article).title
        holder.newsSiteTV.text = getItem(article).newsSite
        holder.summaryTV.text = getItem(article).summary

        //If running on a small width display (i.e. a phone in portrait) support expand to detailed view
        if(holder.cardView.isClickable)
        {
            holder.cardView.setOnClickListener {
                val detailVisibility = holder.detailView.visibility

                if(detailVisibility == View.VISIBLE){
                    holder.detailView.visibility = View.GONE
                    holder.expandDetailsImageButton.setImageResource(R.drawable.ic_expand_more)
                }
                else {
                    holder.detailView.visibility = View.VISIBLE
                    holder.expandDetailsImageButton.setImageResource(R.drawable.ic_expand_less)
                }
            }
        }
    }
}