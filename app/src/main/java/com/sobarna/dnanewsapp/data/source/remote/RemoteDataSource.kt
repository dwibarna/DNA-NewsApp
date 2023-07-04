package com.sobarna.dnanewsapp.data.source.remote

import android.util.Log
import com.sobarna.dnanewsapp.BuildConfig.BASE_API_KEY
import com.sobarna.dnanewsapp.data.source.remote.network.ApiResponse
import com.sobarna.dnanewsapp.data.source.remote.network.ApiService
import com.sobarna.dnanewsapp.data.source.remote.response.NewsResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getHeadlineNews(category: String): Flow<ApiResponse<NewsResponse>> = flow {
        try {
            emit(
                ApiResponse.Success(
                    apiService.getHeadLineNews(
                        apiKey = BASE_API_KEY,
                        country = "us",
                        page = 1,
                        pageSize = 5,
                        category = category
                    )
                )
            )
        } catch (e: Throwable) {
            emit(ApiResponse.Error(e))
            Log.e(javaClass.name, e.message.toString())
        }
    }

    suspend fun getSearchNews(query: String): Flow<ApiResponse<NewsResponse>> = flow {
        try {
            emit(
                ApiResponse.Success(
                    apiService.getSearchNews(
                        apiKey = BASE_API_KEY,
                        query = query
                    )
                )
            )
        } catch (e: Throwable) {
            emit(ApiResponse.Error(e))
            Log.e(javaClass.name, e.message.toString())
        }
    }
}