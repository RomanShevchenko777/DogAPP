package com.example.dog_info.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.dog_info.data.database.DogEntity
import com.example.dog_info.tools.SingleLiveEvent
import kotlinx.coroutines.launch

class ImageViewModel : BaseViewModel() {

    val loadImageLinks = SingleLiveEvent<List<String>?>()

    fun getImageLinks(dogEntity: String) {
        viewModelScope.launch {
            val imageLinkList = repository.getBreedByNameWithDb(dogEntity)

            if (imageLinkList.imageLink.isNullOrEmpty()) {
                val changeBreedForApi = dogEntity.replace(" ", "/")
                val imageLinks = repository.getImagesLinks(changeBreedForApi, 10)
                if (imageLinks.isSuccess) {
                    imageLinks.getOrNull()?.let {
                        val dogEntityLocal = DogEntity(dogEntity, it)
                        repository.insertImage(dogEntityLocal)
                        loadImageLinks.value = it
                    }
                } else {
                    loadErrorCommand.value = imageLinks.exceptionOrNull()?.message
                }
            } else {
                loadImageLinks.value = imageLinkList.imageLink
            }
        }
    }

}