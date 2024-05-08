package com.example.imagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.data.Repository
import com.example.imagesearch.data.SearchResponse

class MainViewModel(private val repository: Repository) : ViewModel() {

    // 이미지 검색 결과를 저장하는 LiveData
    private val _searchResults = MutableLiveData<List<SearchResponse>>()
    val searchResults: LiveData<List<SearchResponse>> get() = _searchResults

    // 선택된 이미지 리스트를 저장하는 LiveData
    private val _selectedImages = MutableLiveData<List<String>>()
    val selectedImages: LiveData<List<String>> get() = _selectedImages
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}