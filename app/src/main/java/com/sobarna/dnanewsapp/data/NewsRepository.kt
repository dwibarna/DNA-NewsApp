package com.sobarna.dnanewsapp.data

import com.sobarna.dnanewsapp.domain.model.News
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getMainHeadlines(): Flow<Resource<MutableList<List<News>>>>

    fun getSearchNews(query: String): Flow<Resource<List<News>>>

    fun getListHistory(): Flow<List<News>>

    suspend fun insertNewsHistory(news: News)
}