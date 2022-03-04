package com.example.dog_info.data.api

import com.example.dog_info.data.model.ImageLinkResponse
import com.example.dog_info.data.model.GetBreedListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface Api {
    @GET("breeds/list/all")
    suspend fun getBreed(): Response<GetBreedListResponse>

    @GET("breed/{link}/images/random/{index}")
    suspend fun getImage(
        @Path("link", encoded = true) link: String,
        @Path("index") index: Int
    ): Response<ImageLinkResponse>
}