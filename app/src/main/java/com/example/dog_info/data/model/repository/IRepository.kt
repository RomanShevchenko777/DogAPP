package com.example.dog_info.data.model.repository

import com.example.dog_info.data.database.DogEntity

interface IRepository {
    suspend fun getBreeds(): Result<List<DogEntity>?>
    suspend fun insertBreeds(dogEntity: List<DogEntity>)
    suspend fun getBreedsWithDb(limit: Int): List<String>
    suspend fun getImagesLinks(breed: String, index: Int): Result<List<String>?>
    suspend fun insertImage(dogEntity: DogEntity)
    suspend fun getBreedByNameWithDb(breed: String): DogEntity
    suspend fun getBreedsInRangeWithDb(start:Int, end: Int): List<String>
}