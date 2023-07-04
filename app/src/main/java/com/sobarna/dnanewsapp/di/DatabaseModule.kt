package com.sobarna.dnanewsapp.di

import android.content.Context
import androidx.room.Room
import com.sobarna.dnanewsapp.data.source.local.room.NewsDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): NewsDatabase =
        Room.databaseBuilder(
            context = context,
            klass = NewsDatabase::class.java,
            name = "news.db"
        )
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    fun provideHistoryDao(database: NewsDatabase) = database.historyDao()
}