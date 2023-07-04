package com.sobarna.dnanewsapp.domain

import com.sobarna.dnanewsapp.data.NewsRepository
import com.sobarna.dnanewsapp.data.Resource
import com.sobarna.dnanewsapp.domain.model.News
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NewsUseCaseImpl @Inject constructor(private val repository: NewsRepository) : NewsUseCase {
    override fun getMainHeadlines(): Flow<Resource<MutableList<List<News>>>> {
        return repository.getMainHeadlines()
    }

    override fun getListHistory(): Flow<List<News>> {
        return repository.getListHistory()
    }

    override suspend fun insertNewsHistory(news: News) = repository.insertNewsHistory(news)

    override fun getSearchNews(query: String): Flow<Resource<List<News>>> {
        return repository.getSearchNews(query)
    }

}