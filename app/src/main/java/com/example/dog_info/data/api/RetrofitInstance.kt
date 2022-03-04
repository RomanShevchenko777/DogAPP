package com.example.dog_info.data.api

import com.example.dog_info.data.model.GetBreedListResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BREED_URL = "https://dog.ceo/api/"

object RetrofitInstance {

    private val client = OkHttpClient.Builder()
        .build()

    private val gson: Gson
        get() {
            return GsonBuilder()
                .registerTypeAdapter(
                    GetBreedListResponse::class.java,
                    GetBreedListResponse.Deserializer()
                )
                .create()
        }

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BREED_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    val api: Api by lazy {
        retrofit.create(Api::class.java)
    }
}