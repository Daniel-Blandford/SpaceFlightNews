package com.danielblandford.workplace.ui.rooms.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface RoomsDao {

    @Query("SELECT * FROM Room ORDER BY id ASC")
    fun getLocalDBRooms(): LiveData<List<Room>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(room: List<Room>)

}
@Database(entities=[Room::class], version=2)
abstract class RoomsDatabase: RoomDatabase(){
    abstract val room: RoomsDao
}