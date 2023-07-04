package com.sobarna.dnanewsapp.presentation.home

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_HTML_TEXT
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.sobarna.dnanewsapp.R
import com.sobarna.dnanewsapp.data.Resource
import com.sobarna.dnanewsapp.databinding.FragmentHomeBinding
import com.sobarna.dnanewsapp.domain.model.News
import com.sobarna.dnanewsapp.presentation.adapter.NewsAdapter
import com.sobarna.dnanewsapp.presentation.adapter.ParentMainAdapter
import com.sobarna.dnanewsapp.presentation.detail_news.DetailNewsActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val viewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initHeadlineNews()
        initActionSearch()
    }

    private fun initActionSearch() {
        binding.searchView.apply {
            queryHint = context.getString(R.string.hint)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query.isNullOrBlank())
                        initHeadlineNews()
                    else
                        initNewsSearch(query)

                    hideKeyboard()

                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText.isNullOrBlank()) {
                        initHeadlineNews()
                        hideKeyboard()
                    }
                    return false
                }
            })
        }
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.searchView.windowToken, 0)
    }

    private fun showLoading(state: Boolean) {
        with(binding) {
            scrollShimmer.isVisible = state
            shimmerLoading.apply {
                if (state) {
                    startShimmer()
                } else {
                    stopShimmer()
                }
            }
            tvState.visibility = GONE
        }
    }

    private fun initNewsSearch(query: String) {
        viewModel.getNewsSearch(query).observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Error -> showError(result.message.toString())
                is Resource.Loading -> {
                    binding.rvMain.visibility = GONE
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    if (result.data.isNullOrEmpty().not()) {
                        val newsAdapter = NewsAdapter(result.data ?: emptyList())
                        binding.rvMain.apply {
                            visibility = VISIBLE
                            layoutManager = LinearLayoutManager(context)
                            adapter = newsAdapter
                        }
                        newsAdapter.setActionClick(
                            actionClick = {
                                initActionClickAdapter(it)
                            }
                        )
                    } else {
                        showEmptyData()
                    }
                }
            }
        }
    }

    private fun showError(error: String) {
        showLoading(false)
        binding.rvMain.visibility = GONE
        binding.tvState.apply {
            visibility = VISIBLE
            text = error
        }
    }

    private fun initActionClickAdapter(news: News) {
        viewModel.insertHistory(news)
        activity?.let { context ->
            val intent = Intent(context, DetailNewsActivity::class.java)
            intent.putExtra(EXTRA_HTML_TEXT, news.url)
            context.startActivity(intent)
        }
    }

    private fun initHeadlineNews() {
        viewModel.getAllHeadlines.observe(viewLifecycleOwner) { result ->
            when (result) {
                is Resource.Error -> showError(result.message.toString())
                is Resource.Loading -> {
                    binding.rvMain.visibility = GONE
                    showLoading(true)
                }
                is Resource.Success -> {
                    showLoading(false)
                    if (result.data.isNullOrEmpty().not()) {
                        val mainAdapter = ParentMainAdapter(result.data ?: emptyList())
                        binding.rvMain.apply {
                            visibility = VISIBLE
                            layoutManager = LinearLayoutManager(context)
                            adapter = mainAdapter
                        }
                        mainAdapter.setActionClick(
                            actionClick = {
                                initActionClickAdapter(it)
                            }
                        )
                    } else {
                        showEmptyData()
                    }
                }
            }
        }
    }

    private fun showEmptyData() {
        binding.rvMain.visibility = GONE
        binding.tvState.apply {
            visibility = VISIBLE
            text = context.getString(R.string.state_empty)
        }
    }
}