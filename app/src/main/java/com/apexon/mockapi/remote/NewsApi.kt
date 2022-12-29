package com.apexon.mockapi.remote

import com.apexon.mockapi.BuildConfig
import com.apexon.mockapi.remote.responses.NewsResponse
import retrofit2.Response
import retrofit2.http.GET

interface NewsApi {
    @GET("top-headlines?country=us&apiKey="+BuildConfig.API_KEY)
        suspend fun getAllNews(): Response<NewsResponse>
}