package com.sobarna.dnanewsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sobarna.dnanewsapp.R
import com.sobarna.dnanewsapp.databinding.ContentFullItemNewsBinding
import com.sobarna.dnanewsapp.databinding.ContentHalfItemNewsBinding
import com.sobarna.dnanewsapp.domain.model.News
import com.sobarna.dnanewsapp.util.Utils.formatDateTime
import com.sobarna.dnanewsapp.util.Utils.loadImageBitmap

class ChildMainAdapter(val list: List<News>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var actionClick: ((News) -> Unit)? = null

    fun setActionClick(actionClick: ((News) -> Unit)? = null) {
        this.actionClick = actionClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == HEADER)
            FullViewHolder(
                ContentFullItemNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else
            HalfViewHolder(
                ContentHalfItemNewsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0)
            HEADER
        else
            OTHER
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is FullViewHolder -> {
                holder.bindItem(list[position])
            }
            is HalfViewHolder -> {
                holder.bindItem(list[position])
            }
        }
    }


    inner class FullViewHolder(private val binding: ContentFullItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(news: News) {
            with(binding) {
                tvTitle.text = news.title
                tvDesc.text = news.description
                tvPublish.text = formatDateTime(news.publishedAt)
                tvNameSource.text = news.name
                loadImageBitmap(
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

    inner class HalfViewHolder(private val binding: ContentHalfItemNewsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(news: News) {
            with(binding) {
                tvTitle.text = news.title
                tvDesc.text = news.description
                tvPublish.text = formatDateTime(news.publishedAt)
                tvNameSource.text = news.name
                loadImageBitmap(
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

    companion object {
        private const val HEADER = 0
        private const val OTHER = 1
    }
}