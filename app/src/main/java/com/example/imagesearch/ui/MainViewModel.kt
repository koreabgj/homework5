package com.example.imagesearch.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.data.SearchResponse
import com.example.imagesearch.network.RetrofitService


class MainViewModel(private val retrofitService: RetrofitService.RetrofitService) : ViewModel() {
    private val _getSearchImageLiveData: MutableLiveData<List<SearchResponse>> = MutableLiveData()
    val getSearchImageLiveData: MutableLiveData<List<SearchResponse>> get() = _getSearchImageLiveData
}

class MainViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(RetrofitService.getInstance()) as T
    }
}