package com.example.dog_info.data.model.repository

import com.example.dog_info.data.api.Api
import com.example.dog_info.data.database.DogDao
import com.example.dog_info.data.database.DogEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.lang.Exception

class Repository(
    private val api: Api,
    private val dao: DogDao,
    private val ioDispatcher: CoroutineDispatcher
) : IRepository {
    override suspend fun getBreeds(): Result<List<DogEntity>?> {
        return withContext(ioDispatcher) {
            val response = try {
                api.getBreed()
            } catch (e: Throwable) {
                return@withContext Result.failure(e)
            }
            when {
                response.isSuccessful && response.body() != null -> {
                    Result.success(response.body()?.breedList)
                }
                else -> {
                    Result.failure(Exception(response.message()))
                }
            }
        }
    }

    override suspend fun insertBreeds(dogEntity: List<DogEntity>) {
        withContext(ioDispatcher) {
            dao.insertBreeds(dogEntity)
        }
    }

    override suspend fun getBreedsWithDb(limit: Int): List<String> {
        return withContext(ioDispatcher) {
            dao.getBreeds(limit)
        }
    }

    override suspend fun getImagesLinks(breed: String, index: Int): Result<List<String>?> {
        return withContext(ioDispatcher) {
            val response = try {
                api.getImage(breed, 10)
            } catch (e: Throwable) {
                return@withContext Result.failure(e)
            }
            when {
                response.isSuccessful && response.body() != null -> {
                    Result.success(response.body()?.imageLink)
                }
                else -> {
                    Result.failure(Exception(response.message()))
                }
            }
        }
    }

    override suspend fun insertImage(dogEntity: DogEntity) {
        withContext(ioDispatcher) {
            dao.insertDogImages(dogEntity)
        }
    }

    override suspend fun getBreedByNameWithDb(breed: String): DogEntity {
        return withContext(ioDispatcher) {
            dao.getBreedByName(breed)
        }
    }

    override suspend fun getBreedsInRangeWithDb(start:Int, end: Int): List<String> {
        return withContext(ioDispatcher) {
            dao.getBreedsInRange(start, end)
        }
    }
}