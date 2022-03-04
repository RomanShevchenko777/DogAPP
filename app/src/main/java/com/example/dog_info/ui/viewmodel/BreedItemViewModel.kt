package com.example.dog_info.ui.viewmodel

import androidx.databinding.ObservableField
import com.example.dog_info.data.database.DogEntity

class BreedItemViewModel {
    val breedText = ObservableField<String>()

    fun bind(breed: String) {
        breedText.set(breed)
    }
}