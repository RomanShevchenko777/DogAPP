package com.example.dog_info.data.database

import android.content.Context
import androidx.room.Room
import com.example.dog_info.R

object DbFactory {
    lateinit var database: DogDatabase

    fun init(context: Context){
        if(!this::database.isInitialized) {
            database = Room.databaseBuilder(
                context, DogDatabase::class.java, context.getString(R.string.room_database)
            ).allowMainThreadQueries().build()
        }
    }

    val dao: DogDao by lazy {
        database.getDogDao()
    }
}