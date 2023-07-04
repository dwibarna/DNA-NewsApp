package com.sobarna.dnanewsapp.data.source.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo("name")
    val name: String?,

    @ColumnInfo("title")
    val title: String?,

    @ColumnInfo("description")
    val description: String?,

    @ColumnInfo("url")
    val url: String?,

    @ColumnInfo("urlToImage")
    val urlToImage: String?,

    @ColumnInfo("publishedAt")
    val publishedAt: String?,
)