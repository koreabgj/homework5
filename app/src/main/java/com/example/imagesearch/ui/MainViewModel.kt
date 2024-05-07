package com.example.imagesearch.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.data.Repository
import com.example.imagesearch.data.SearchResponse

class MainViewModel(private val repository: Repository) : ViewModel() {

    // 선택된 이미지 리스트를 저장하는 LiveData
    private val _selectedImages = MutableLiveData<List<String>>()
    val selectedImages: LiveData<List<String>> get() = _selectedImages

    // 이미지 검색 결과를 저장하는 LiveData
    private val _searchResults = MutableLiveData<List<SearchResponse>>()
    val searchResults: LiveData<List<SearchResponse>> get() = _searchResults

//    // 이미지 검색을 실행하는 메서드
//    suspend fun searchImages(apiKey: String, query: String, sort: String, page: Int, size: Int) {
//        try {
//            val response = repository.searchImages(apiKey, query, sort, page, size)
//            // 검색 결과에 대한 처리를 여기에 추가할 수 있습니다.
//        } catch (e: Exception) {
//            Log.e(TAG, "Error fetching search images: $e")
//            // 예외 처리를 할 수도 있습니다.
//        }
//    }
//
//    // 이미지를 선택하는 메서드
//    fun addSelectedImage(imageUrl: String) {
//        val currentList = _selectedImages.value ?: emptyList()
//        _selectedImages.value = currentList + imageUrl
//    }
//
//    // 선택된 이미지를 제거하는 메서드
//    fun removeSelectedImage(imageUrl: String) {
//        val currentList = _selectedImages.value ?: emptyList()
//        _selectedImages.value = currentList - imageUrl
//    }
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}