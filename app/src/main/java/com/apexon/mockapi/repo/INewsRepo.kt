package com.apexon.mockapi.repo

import com.apexon.mockapi.remote.responses.NewsResponse
import retrofit2.Response

interface INewsRepo {

    suspend fun getNews() : Response<NewsResponse>

}