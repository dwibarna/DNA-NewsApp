package com.sobarna.dnanewsapp.domain

import com.sobarna.dnanewsapp.data.Resource
import com.sobarna.dnanewsapp.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsUseCase {

    fun getMainHeadlines(): Flow<Resource<MutableList<List<News>>>>

    fun getListHistory(): Flow<List<News>>

    suspend fun insertNewsHistory(news: News)

    fun getSearchNews(query: String): Flow<Resource<List<News>>>
}