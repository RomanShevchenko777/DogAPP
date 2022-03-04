package com.example.dog_info.data.database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.google.gson.Gson
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class DogEntity(
    @PrimaryKey
    val breed: String,
    val imageLink: List<String>? = null
) : Parcelable
