package com.danielblandford.workplace.ui.rooms.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Room (
    @PrimaryKey
    var id: Int,
    var name: String,
    var max_occupancy: Int,
    var is_occupied: Boolean
)
fun List<Room>.asDomainModel():List<Room> {
    return map {
        Room(
            id = it.id,
            name = it.name,
            max_occupancy = it.max_occupancy,
            is_occupied = it.is_occupied
        )
    }
}