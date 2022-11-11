package com.danielblandford.spaceflightnews.ui.articles.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.danielblandford.spaceflightnews.utils.DatabaseTypeConverter

@Dao
interface ArticleDao {

    @Query("SELECT * FROM Article ORDER BY id ASC")
    fun getLocalDBArticles(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(articles: List<Article>)

}
@Database(entities=[Article::class], version=1)
@TypeConverters(DatabaseTypeConverter::class)
abstract class ArticlesDatabase: RoomDatabase(){
    abstract val articles: ArticleDao
}