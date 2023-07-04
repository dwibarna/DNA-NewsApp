package com.sobarna.dnanewsapp.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sobarna.dnanewsapp.data.source.local.entity.HistoryEntity

@Database(
    entities = [HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun historyDao(): HistoryDao
}