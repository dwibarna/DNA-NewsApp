package com.sobarna.dnanewsapp.util

import android.content.Context
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.sobarna.dnanewsapp.data.source.local.entity.HistoryEntity
import com.sobarna.dnanewsapp.data.source.remote.response.NewsResponse
import com.sobarna.dnanewsapp.domain.model.News
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {

    private val inputFormats = listOf(
        "yyyy-MM-dd'T'HH:mm:ss'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",
        "yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'",
    )

    val listCategory = listOf(
        "general",
        "Business",
        "Entertainment",
        "Health",
        "Science",
        "Sports",
        "Technology"
    )

    fun mapperResponseToDomain(response: NewsResponse): List<News> {
        val mutableNews: MutableList<News> = mutableListOf()
        response.articles?.forEach {
            News(
                id = 0,
                name = it.source?.name ?: "",
                title = it.title ?: "",
                description = it.description ?: "No Description",
                url = it.url ?: "",
                urlToImage = it.urlToImage ?: "",
                publishedAt = it.publishedAt ?: ""
            ).let(mutableNews::add)
        }
        return mutableNews
    }

    fun loadImageBitmap(context: Context, placeHolder: Int, uri: String, imageView: ImageView) {
        Glide.with(context)
            .asBitmap()
            .placeholder(placeHolder)
            .apply(RequestOptions().centerCrop())
            .load(uri)
            .into(imageView)
    }


    fun formatDateTime(dateTimeString: String): String {
        val outputFormat = SimpleDateFormat("E, d MMMM HH.mm", Locale("id", "ID"))

        inputFormats.forEach {
            val inputFormat = SimpleDateFormat(it, Locale("id", "ID"))
            try {
                val date = inputFormat.parse(dateTimeString) as Date
                return outputFormat.format(date)
            } catch (e: ParseException) {
                Log.e(javaClass.name, e.message.toString())
            }
        }
        return ""
    }


    fun mapperEntityToDomain(entities: List<HistoryEntity>): List<News> {
        val mutableNews: MutableList<News> = mutableListOf()
        entities.forEach {
            News(
                id = it.id,
                name = it.name ?: "",
                title = it.title ?: "",
                description = it.description ?: "",
                url = it.url ?: "",
                urlToImage = it.urlToImage ?: "",
                publishedAt = it.publishedAt ?: ""
            ).let(mutableNews::add)
        }
        return mutableNews
    }

    fun mapperDomainToEntity(news: News): HistoryEntity {
        return HistoryEntity(
            id = 0,
            name = news.name,
            title = news.title,
            description = news.description,
            url = news.url,
            urlToImage = news.urlToImage,
            publishedAt = news.publishedAt
        )
    }


}