package com.example.dog_info.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [DogEntity::class], version = 1)
@TypeConverters(Converters::class)
abstract class DogDatabase : RoomDatabase() {
    abstract fun getDogDao(): DogDao
}