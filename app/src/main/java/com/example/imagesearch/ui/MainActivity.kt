package com.example.imagesearch.ui

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imagesearch.databinding.ActivityMainBinding
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.R
import com.example.imagesearch.data.Repository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 앱 실행시 SearchFragment
        if (savedInstanceState == null) {
            showFragment(SearchFragment(), R.id.fragment_container_search)
        }

        // 이미지 검색 클릭시 SearchFragment
        binding.btnSearch.setOnClickListener {
            showFragment(SearchFragment(), R.id.fragment_container_search)
        }

        // 이미지 저장 클릭시 KeepFragment
        binding.btnKeep.setOnClickListener {
            showFragment(KeepFragment(), R.id.fragment_container_keep)
        }

        // 검색 실행
        binding.btnExecuteSearch.setOnClickListener {
            searchImages()
            setLastSearchToSearchField()
            hideKeyboard(it)
        }

        observeViewModel()

        val dateTime = Date()
        formatDateTime(dateTime)
    }

    private fun showFragment(fragment: Fragment, containerId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(containerId, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeViewModel() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]
        viewModel.searchImages(
            authorization = "KakaoAK d7dad5f8832c904973babb0a21d079ab",
            query = "검색어",
            sort = "accuracy",
            page = 1,
            size = 10
        )
    }

    // 검색어 저장
    private fun searchImages() {
        val searchQuery = binding.etSearch.text.toString()
        saveLastSearch(searchQuery)
    }

    private fun saveLastSearch(searchQuery: String) {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString(LAST_SEARCH_KEY, searchQuery)
        editor.apply()
    }

    // 마지막 검색어를 검색창에 표시
    private fun setLastSearchToSearchField() {
        val lastSearch = getLastSearch()
        lastSearch?.let {
            binding.etSearch.setText(it)
            binding.etSearch.setSelection(it.length)
        }
    }

    private fun getLastSearch(): String? {
        val sharedPreferences = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return sharedPreferences.getString(LAST_SEARCH_KEY, null)
    }

    private fun hideKeyboard(view: View) {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun formatDateTime(dateTime: Date): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(dateTime)
    }

    // Bundle 객체를 생성하여 KeepFragment에 전달할 데이터로 thumbnailUrl을 설정
    fun navigateToKeepFragment(thumbnailUrl: String) {
        val bundle = Bundle().apply {
            putString(KeepFragment.THUMBNAIL_URLS_KEY, thumbnailUrl)
        }
        val keepFragment = KeepFragment().apply {
            arguments = bundle
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, keepFragment)
            .addToBackStack(null)
            .commit()
    }

    companion object {
        private const val PREFS_NAME = "SearchPrefs"
        private const val LAST_SEARCH_KEY = "last_search"
    }
}