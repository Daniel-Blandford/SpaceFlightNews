package com.danielblandford.workplace.ui.people.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface PersonDao {

    @Query("SELECT * FROM Person ORDER BY firstName ASC")
    fun getLocalDBPeople(): LiveData<List<Person>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(people: List<Person>)

}
@Database(entities=[Person::class], version=2)
abstract class PeopleDatabase: RoomDatabase(){
    abstract val people: PersonDao
}