package com.sobarna.dnanewsapp.di

import com.sobarna.dnanewsapp.data.NewsRepository
import com.sobarna.dnanewsapp.data.NewsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(
    includes = [
        DatabaseModule::class,
        NetworkModule::class
    ]
)
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: NewsRepositoryImpl): NewsRepository
}