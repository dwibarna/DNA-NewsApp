package com.sobarna.dnanewsapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sobarna.dnanewsapp.databinding.ContentMainItemBinding
import com.sobarna.dnanewsapp.domain.model.News
import com.sobarna.dnanewsapp.util.Utils.listCategory

class ParentMainAdapter(val list: List<List<News>>) :
    RecyclerView.Adapter<ParentMainAdapter.ViewHolder>() {

    private var actionClick: ((News) -> Unit)? = null

    fun setActionClick(actionClick: ((News) -> Unit)? = null) {
        this.actionClick = actionClick
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ContentMainItemBinding.inflate(
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
        holder.bindItem(Pair(list[position], listCategory[position]))
    }

    inner class ViewHolder(private val binding: ContentMainItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindItem(list: Pair<List<News>, String>) {
            binding.tvTitleMain.apply {
                isVisible = list.second != "general"
                text = list.second
            }

            val childAdapter = ChildMainAdapter(list.first)
            childAdapter.setActionClick(
                actionClick = {
                    actionClick?.invoke(it)
                }
            )
            binding.rvHead.apply {
                layoutManager = GridLayoutManager(context, 2)
                adapter = childAdapter
                (layoutManager as? GridLayoutManager)?.apply {
                    spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == 0) 2 else 1
                        }
                    }
                }
            }
        }
    }
}