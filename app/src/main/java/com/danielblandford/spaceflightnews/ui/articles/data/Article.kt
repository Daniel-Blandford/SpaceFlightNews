package com.danielblandford.spaceflightnews.ui.articles.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity
data class Article(
    @PrimaryKey
    val id: Int,
    val events: List<String>?,
    val featured: Boolean,
    val imageUrl: String,
    val launches: List<Launches>,
    val newsSite: String,
    val publishedAt: String,
    val summary: String,
    val title: String,
    val updatedAt: String,
    val url: String
)

    @Serializable
    data class Launches(
        val id: String,
        val provider: String
    )


fun List<Article>.asDomainModel():List<Article> {
    return map {
        Article(
            id = it.id,
            events = it.events,
            featured = it.featured,
            imageUrl = it.imageUrl,
            launches = it.launches,
            newsSite = it.newsSite,
            publishedAt = it.publishedAt,
            summary = it.summary,
            title = it.title,
            updatedAt = it.updatedAt,
            url = it.url,
        )
    }
}
