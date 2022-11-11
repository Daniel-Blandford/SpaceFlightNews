package com.danielblandford.spaceflightnews.utils

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.danielblandford.spaceflightnews.ui.articles.data.Event
import com.danielblandford.spaceflightnews.ui.articles.data.Launches
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

@ProvidedTypeConverter
object DatabaseTypeConverter {

    @TypeConverter
    fun stringToListOfStrings(value: String): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listOfStringsToString(list: List<String>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToListOfLaunches(value: String): List<Launches> {
        val listType: Type = object : TypeToken<List<Launches?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listOfLaunchesToString(list: List<Launches>): String {
        val gson = Gson()
        return gson.toJson(list)
    }

    @TypeConverter
    fun stringToListOfEvents(value: String): List<Event> {
        val listType: Type = object : TypeToken<List<Event?>?>() {}.type
        return Gson().fromJson(value, listType)
    }

    @TypeConverter
    fun listOfEventsToString(list: List<Event>): String {
        val gson = Gson()
        return gson.toJson(list)
    }
}