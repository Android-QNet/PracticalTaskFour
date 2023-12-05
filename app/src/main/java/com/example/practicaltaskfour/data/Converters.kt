package com.example.practicaltaskfour.data

import androidx.room.TypeConverter
import com.example.practicaltaskfour.model.Poll
import com.google.gson.Gson

class Converters {

    @TypeConverter
    fun listToJson(value: List<Poll>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<Poll>::class.java).toList()
}