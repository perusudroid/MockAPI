package com.apexon.mockapi.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.apexon.mockapi.common.ResultOf
import com.apexon.mockapi.remote.responses.NewsResponse
import com.apexon.mockapi.repo.INewsRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val repo: INewsRepo): ViewModel() {

    private val _news = MutableLiveData<ResultOf<NewsResponse>>()
    val news: LiveData<ResultOf<NewsResponse>> = _news

    init {
       getNews()
    }

    fun getNews() {
        viewModelScope.launch {
            val response = repo.getNews()
            if(response.isSuccessful && response.body() != null){
                _news.value = ResultOf.success(response.body())
            }else{
                _news.value = ResultOf.error(response.errorBody().toString(), response.body())
            }
        }
    }

}