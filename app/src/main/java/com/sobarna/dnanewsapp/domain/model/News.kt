package com.sobarna.dnanewsapp.domain.model

data class News(

    val id: Int = 0,

    val name: String,

    val title: String,

    val description: String,

    val url: String,

    val urlToImage: String,

    val publishedAt: String
)