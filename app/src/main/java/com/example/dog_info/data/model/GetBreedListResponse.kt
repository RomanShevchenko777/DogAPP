package com.example.dog_info.data.model

import com.example.dog_info.data.database.DogEntity
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.annotations.SerializedName
import java.lang.reflect.Type

data class GetBreedListResponse(
    @SerializedName("message")
    val breedList: List<DogEntity>
) {

    class Deserializer : JsonDeserializer<GetBreedListResponse> {
        override fun deserialize(
            json: JsonElement?,
            typeOfT: Type?,
            context: JsonDeserializationContext?
        ): GetBreedListResponse {
            val jsonObject = json?.asJsonObject
            val breedList = mutableListOf<DogEntity>()
            val breadsMap = jsonObject?.get("message")?.asJsonObject?.entrySet()

            breadsMap?.forEach {
                if (!it.value.asJsonArray.isEmpty) {
                    it.value.asJsonArray.forEach { it1 ->
                        breedList.add(DogEntity("${it.key} ${it1.asString}"))
                    }
                } else {
                    breedList.add(DogEntity(it.key))
                }
            }
            return GetBreedListResponse(breedList)
        }
    }
}
