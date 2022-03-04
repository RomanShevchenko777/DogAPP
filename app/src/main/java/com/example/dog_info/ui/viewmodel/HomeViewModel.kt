package com.example.dog_info.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.example.dog_info.tools.SingleLiveEvent
import kotlinx.coroutines.launch

class HomeViewModel : BaseViewModel() {

    private var page = 0
    private val limit = 20

    val loadBreedsCommand = SingleLiveEvent<List<String>>()
    val loadRangeBreedsCommand = SingleLiveEvent<List<String>>()
    val goToImagesCommand = SingleLiveEvent<String>()

    fun getBreeds() {
        viewModelScope.launch {
            progress.set(true)
            val breedList = repository.getBreedsWithDb(limit)
            if (breedList.isEmpty()) {
                val breeds = repository.getBreeds()
                if (breeds.isSuccess) {
                    breeds.getOrNull()?.let { it ->
                        repository.insertBreeds(it)
                        loadBreedsCommand.value = it.map {
                            it.breed
                        }.take(limit)
                    }
                } else {
                    loadErrorCommand.value = breeds.exceptionOrNull()?.message
                }
            } else {
                loadBreedsCommand.value = breedList
            }
            progress.set(false)
        }
    }

    fun navigateToImages(dogEntity: String) {
        goToImagesCommand.value = dogEntity
    }

    fun getPage() {
        progress.set(true)
        val start = ((page) * limit) + 1
        val amount = (page + 1) * limit - start + 1

        viewModelScope.launch {
            val breeds = repository.getBreedsInRangeWithDb(start, amount)
            loadRangeBreedsCommand.value = breeds
        }
        progress.set(false)
    }

    fun isLoading() = progress.get()

    fun increasePage() = page++

    fun resetPage() {
        page = 0
    }

}