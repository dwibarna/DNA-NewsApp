package com.sobarna.dnanewsapp.data.source.local

import com.sobarna.dnanewsapp.data.source.local.entity.HistoryEntity
import com.sobarna.dnanewsapp.data.source.local.room.HistoryDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val dao: HistoryDao) {

    fun getAllHistoryNews(): Flow<List<HistoryEntity>> = dao.getAllHistoryNews()

    suspend fun insertHistoryNews(entity: HistoryEntity) =
        dao.insertHistoryNews(entity)

}