package com.sobarna.dnanewsapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.sobarna.dnanewsapp.domain.NewsUseCase
import com.sobarna.dnanewsapp.domain.model.News
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val useCase: NewsUseCase) : ViewModel() {

    val getAllHeadlines = useCase.getMainHeadlines().asLiveData()

    fun insertHistory(news: News) = viewModelScope.launch {
        useCase.insertNewsHistory(news)
    }

    fun getNewsSearch(query: String) = useCase.getSearchNews(query).asLiveData()
}