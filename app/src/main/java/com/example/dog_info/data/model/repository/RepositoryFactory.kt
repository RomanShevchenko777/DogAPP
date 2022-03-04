package com.example.dog_info.data.model.repository

import com.example.dog_info.data.api.RetrofitInstance
import com.example.dog_info.data.database.DbFactory
import kotlinx.coroutines.Dispatchers

class RepositoryFactory {

    companion object{

        @Volatile
        private var INSTANCE: IRepository? = null

        fun getInstance(): IRepository {
            return INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildRepositoryInstance().also { INSTANCE = it }
            }
        }

        private fun buildRepositoryInstance(): IRepository {
            return Repository(RetrofitInstance.api, DbFactory.dao, Dispatchers.IO)
        }
    }
}