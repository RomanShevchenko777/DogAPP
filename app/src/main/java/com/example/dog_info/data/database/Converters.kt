package com.example.dog_info.data.database

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJson(list: List<String>?): String = Gson().toJson(list)

    @TypeConverter
    fun jsonToList(value: String): List<String> {
        return if (!value.contains("null")) {
            Gson().fromJson(value, ArrayList<String>()::class.java).toList()
        } else {
            listOf()
        }
    }
}