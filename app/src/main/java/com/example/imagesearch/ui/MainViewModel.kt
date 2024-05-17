package com.example.imagesearch.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.ImageDocuments
import com.example.imagesearch.data.Repository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: Repository) : ViewModel() {

    private val _thumbnailUrls = MutableLiveData<List<String>>()
    val thumbnailUrlList: LiveData<List<String>> = _thumbnailUrls

    private val _imageDocuments = MutableLiveData<List<ImageDocuments>>()
    val imageDocuments: LiveData<List<ImageDocuments>> = _imageDocuments
    fun searchImages(query: String, sort: String, page: Int, size: Int) {
        viewModelScope.launch {
            val result = repository.searchImages(query, sort, page, size)
            _imageDocuments.value = result
        }
    }


    fun setThumbnailUrls(thumbnailUrls: List<String>) {
        _thumbnailUrls.value = thumbnailUrls
    }

}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}