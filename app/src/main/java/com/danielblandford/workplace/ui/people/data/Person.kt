package com.danielblandford.workplace.ui.people.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class Person(
    @PrimaryKey
    var id: Int,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phone: String,
    var jobTitle: String,
    var avatar: String,
    var latitude: Double,
    var longitude: Double
): Parcelable

fun List<Person>.asDomainModel():List<Person> {
    return map {
        Person(
            id = it.id,
            firstName = it.firstName,
            lastName = it.lastName,
            email = it.email,
            phone = it.phone,
            jobTitle = it.jobTitle,
            avatar = it.avatar,
            latitude = it.latitude,
            longitude = it.longitude,
        )
    }
}