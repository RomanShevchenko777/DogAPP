package com.example.dog_info.data.model

import com.google.gson.annotations.SerializedName

data class ImageLinkResponse(
    @SerializedName("message")
    val imageLink: List<String>
)