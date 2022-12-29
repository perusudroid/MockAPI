package com.apexon.mockapi.repo

import com.apexon.mockapi.remote.NewsApi

class NewsRepo(private val iNewsApi: NewsApi) : INewsRepo {

    override suspend fun getNews() = iNewsApi.getAllNews()

}