package com.danielblandford.workplace.ui.rooms.data

data class Room(
    val created_at: String,
    val id: String,
    val is_occupied: Boolean,
    val max_occupancy: Int,
    val name: String
)
