package com.apexon.mockapi.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.apexon.mockapi.common.CountingIdlingResourceSingleton
import com.apexon.mockapi.common.Status
import com.apexon.mockapi.databinding.ActivityNewsBinding
import com.apexon.mockapi.vm.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {

    private val binding by lazy{ _binding!! }
    private var _binding : ActivityNewsBinding?=null
    private val newsAdapter by lazy { NewsAdapter(::callback) }
    private val newsViewModel : NewsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        CountingIdlingResourceSingleton.increment()
        setAssets()
        subscribeToObservers()
    }

    private fun setAssets() {
        binding.rvNews.apply {
            layoutManager = LinearLayoutManager(this@NewsActivity)
            setHasFixedSize(true)
            adapter = newsAdapter
        }
    }

    private fun callback(title : String?){
        binding.tvNews.text = title
    }

    private fun subscribeToObservers() {
        newsViewModel.news.observe(this){
            when(it.status){
                Status.LOADING -> {}
                Status.SUCCESS -> {
                    newsAdapter.list = it.data?.articles
                    CountingIdlingResourceSingleton.decrement()
                }
                Status.ERROR -> {
                    Toast.makeText(this, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}