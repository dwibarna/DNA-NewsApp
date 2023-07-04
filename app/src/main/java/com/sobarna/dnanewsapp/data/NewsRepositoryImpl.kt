package com.sobarna.dnanewsapp.data

import android.util.Log
import com.sobarna.dnanewsapp.data.source.local.LocalDataSource
import com.sobarna.dnanewsapp.data.source.remote.RemoteDataSource
import com.sobarna.dnanewsapp.data.source.remote.network.ApiResponse
import com.sobarna.dnanewsapp.domain.model.News
import com.sobarna.dnanewsapp.util.Utils.listCategory
import com.sobarna.dnanewsapp.util.Utils.mapperDomainToEntity
import com.sobarna.dnanewsapp.util.Utils.mapperEntityToDomain
import com.sobarna.dnanewsapp.util.Utils.mapperResponseToDomain
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepositoryImpl @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) : NewsRepository {
    override fun getMainHeadlines(): Flow<Resource<MutableList<List<News>>>> = flow {
        val listCategoryNews: MutableList<List<News>> = mutableListOf()
        listCategoryNews.clear()
        emit(Resource.Loading())
        try {
            listCategory.forEach {
                when (val response = remoteDataSource.getHeadlineNews(it).single()) {
                    is ApiResponse.Error -> {
                        emit(Resource.Error(message = response.error.toString()))
                        Log.e(javaClass.name, response.error.message.toString())
                    }
                    is ApiResponse.Success -> {
                        mapperResponseToDomain(response.data).let(listCategoryNews::add)
                    }
                }
            }
            emit(Resource.Success(data = listCategoryNews))
        } catch (e: Throwable) {
            emit(Resource.Error(message = e.message.toString()))
        }
    }

    override fun getSearchNews(query: String): Flow<Resource<List<News>>> = flow {
        emit(Resource.Loading())
        try {
            when (val response = remoteDataSource.getSearchNews(query = query).single()) {
                is ApiResponse.Error -> {
                    emit(Resource.Error(message = response.error.toString()))
                    Log.e(javaClass.name, response.error.message.toString())
                }
                is ApiResponse.Success -> {
                    emit(Resource.Success(mapperResponseToDomain(response.data)))
                }
            }
        } catch (e: Throwable) {
            emit(Resource.Error(message = e.message.toString()))
            Log.e(javaClass.name, e.message.toString())
        }
    }

    override fun getListHistory(): Flow<List<News>> = flow {
        emitAll(localDataSource.getAllHistoryNews().map {
            mapperEntityToDomain(it)
        })
    }

    override suspend fun insertNewsHistory(news: News) {
        localDataSource.insertHistoryNews(mapperDomainToEntity(news))
    }
}