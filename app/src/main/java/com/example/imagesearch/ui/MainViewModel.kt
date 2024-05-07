package com.example.imagesearch.ui

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.imagesearch.data.SearchResponse
import com.example.imagesearch.data.Repository
import kotlinx.coroutines.launch


class MainViewModel(private val retrofitService: Repository) : ViewModel() {
    fun getSearchImages(searchQuery: String) {
        // Retrofit 서비스를 사용하여 이미지 검색을 시작하고 결과를 처리하는 비동기 작업 수행
        viewModelScope.launch {
            try {
                // 네트워크 요청 보내기
                val response = Repository.searchImages(searchQuery)
                // 요청 결과를 LiveData에 업데이트
                _getSearchImageLiveData.value = response
            } catch (e: Exception) {
                // 오류 처리
                Log.e(TAG, "Error fetching search images: $e")
            }
        }
    }

    fun searchResultsLiveData(mainActivity: MainActivity, observer: Observer<List<SearchResponse>>) {
        // LiveData를 MainActivity와 연결
        getSearchImageLiveData.observe(mainActivity, observer)
    }


    val searchResultsLiveData by lazy {  }
    private val _getSearchImageLiveData: MutableLiveData<List<SearchResponse>> = MutableLiveData()
    val getSearchImageLiveData: MutableLiveData<List<SearchResponse>> get() = _getSearchImageLiveData
}

class MainViewModelFactory(private val repository: Repository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(repository) as T
    }
}