package com.example.dog_info.ui.viewmodel

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dog_info.data.model.repository.IRepository
import com.example.dog_info.data.model.repository.RepositoryFactory

abstract class BaseViewModel : ViewModel() {

    val progress = ObservableBoolean()

    val loadErrorCommand = MutableLiveData<String>()

    val repository: IRepository = RepositoryFactory.getInstance()

}