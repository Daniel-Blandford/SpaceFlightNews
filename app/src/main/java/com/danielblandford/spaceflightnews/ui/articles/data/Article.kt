package com.danielblandford.spaceflightnews.ui.articles.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Entity
data class Article(
    @PrimaryKey
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("events")
    val events: List<Event?>? = listOf(),
    @SerializedName("featured")
    val featured: Boolean? = false,
    @SerializedName("imageUrl")
    val imageUrl: String? = "",
    @SerializedName("launches")
    val launches: List<Launches?>? = listOf(),
    @SerializedName("newsSite")
    val newsSite: String? = "",
    @SerializedName("publishedAt")
    val publishedAt: String? = "",
    @SerializedName("summary")
    val summary: String? = "",
    @SerializedName("title")
    val title: String? = "",
    @SerializedName("updatedAt")
    val updatedAt: String? = "",
    @SerializedName("url")
    val url: String? = ""
)

    @Serializable
    data class Launches(
        @SerializedName("id")
        val id: String? = "",
        @SerializedName("provider")
        val provider: String? = ""
    )


data class Event(
    @SerializedName("id")
    val id: Int? = 0,
    @SerializedName("provider")
    val provider: String? = ""
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
