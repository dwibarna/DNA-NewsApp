package com.sobarna.dnanewsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sobarna.dnanewsapp.R
import com.sobarna.dnanewsapp.databinding.ContentFullItemNewsBinding
import com.sobarna.dnanewsapp.domain.model.News
import com.sobarna.dnanewsapp.util.Utils

class NewsAdapter(val list: List<News>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private var actionClick: ((News) -> Unit)? = null

    fun setActionClick(actionClick: ((News) -> Unit)? = null) {
        this.actionClick = actionClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContentFullItemNewsBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItem(list[position])
    }

    inner class ViewHolder(private val binding: ContentFullItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(news: News) {
            with(binding) {
                tvTitle.text = news.title
                tvDesc.text = news.description
                tvPublish.text = Utils.formatDateTime(news.publishedAt)
                tvNameSource.text = news.name
                Utils.loadImageBitmap(
                    context = itemView.context,
                    placeHolder = R.drawable.news_placeholder,
                    imageView = ivImageNews,
                    uri = news.urlToImage
                )
                itemView.setOnClickListener {
                    actionClick?.invoke(news)
                }
            }
        }
    }
}