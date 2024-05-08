package com.example.imagesearch.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.imagesearch.databinding.ActivityMainBinding
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.imagesearch.R
import com.example.imagesearch.data.Repository
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        setUpViews()
        observeViewModel()

        val dateTime by lazy { }
        formatDateTime(dateTime)
    }

    private fun setUpViews() {
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.btnSearch.setOnClickListener {
            showFragment(SearchFragment())
        }

        binding.btnExecuteSearch.setOnClickListener {
            searchImages()
            setLastSearchToSearchField()
            hideKeyboard(it)
        }

        binding.btnKeep.setOnClickListener {
            showFragment(KeepFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.fragments.forEach { it.view?.visibility = View.GONE }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun observeViewModel() {
        val repository = Repository()
        val viewModelFactory = MainViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[MainViewModel::class.java]

        viewModel.searchResults.observe(this, Observer {
            SearchAdapter.notifyItemChanged()
        })

        viewModel.selectedImages.observe(this, Observer {
            KeepAdapter.notifyItemChanged()
        })
    }

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

    private fun formatDateTime(dateTime: Unit): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return dateFormat.format(dateTime)
    }

    fun navigateToKeepFragment(imageUrl: String) {
        val bundle = Bundle().apply {
            putString(KeepFragment.THUMBNAIL_URLS_KEY, imageUrl)
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