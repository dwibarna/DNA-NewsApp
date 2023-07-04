package com.sobarna.dnanewsapp.presentation.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sobarna.dnanewsapp.R
import com.sobarna.dnanewsapp.databinding.FragmentHistoryBinding
import com.sobarna.dnanewsapp.presentation.adapter.NewsAdapter
import com.sobarna.dnanewsapp.presentation.detail_news.DetailNewsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private val viewModel: HistoryViewModel by viewModels()

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getAllHistory.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty().not()) {
                val historyAdapter = NewsAdapter(it)
                binding.tvState.visibility = GONE
                binding.rvHistory.apply {
                    visibility = VISIBLE
                    adapter = historyAdapter
                    layoutManager = LinearLayoutManager(context)
                }
                historyAdapter.setActionClick(
                    actionClick = {
                        activity?.let { context ->
                            val intent = Intent(context, DetailNewsActivity::class.java)
                            intent.putExtra(Intent.EXTRA_HTML_TEXT, it.url)
                            context.startActivity(intent)
                        }
                    }
                )
            } else {
                binding.rvHistory.visibility = GONE
                binding.tvState.apply {
                    visibility = VISIBLE
                    text = context.getString(R.string.state_empty_history)
                }

            }
        }
    }

}