package com.example.imagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.Repository
import com.example.imagesearch.data.SearchResponse
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    fun searchImages(authorization: String, query: String, sort: String, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.searchImages(authorization, query, sort, page, size)
        }
    }

    private val _searchResults = MutableLiveData<List<SearchResponse>>()
    val searchResults: LiveData<List<SearchResponse>> get() = _searchResults

    private val _selectedImages = MutableLiveData<List<String>>()
    val selectedImages: LiveData<List<String>> get() = _selectedImages
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}