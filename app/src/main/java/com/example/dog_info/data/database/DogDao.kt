package com.example.dog_info.data.database

import androidx.room.*

@Dao
interface DogDao {

    @Query("SELECT breed FROM DogEntity LIMIT :limit")
    suspend fun getBreeds(limit: Int = 20): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBreeds(breed: List<DogEntity>)

    @Query("SELECT * FROM DogEntity WHERE breed = :dogBreed")
    suspend fun getBreedByName(dogBreed: String): DogEntity

    @Query("SELECT breed FROM DogEntity LIMIT :start, :end")
    suspend fun getBreedsInRange(start: Int, end: Int): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDogImages(dogEntity: DogEntity)

}