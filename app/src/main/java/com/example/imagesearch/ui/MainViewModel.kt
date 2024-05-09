package com.example.imagesearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun searchImages(authorization: String, query: String, sort: String, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.searchImages(authorization, query, sort, page, size)
        }
    }

    val _thumbnailUrls = MutableLiveData<List<String>>()
    fun setThumbnailUrls(thumbnailUrls: List<String>) {
        _thumbnailUrls.value = thumbnailUrls
    }

    val thumbnailUrlList = MutableLiveData<List<String>>()
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}