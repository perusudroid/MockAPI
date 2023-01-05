package com.apexon.mockapi.repo

import com.apexon.mockapi.remote.NewsApi
import com.apexon.mockapi.remote.responses.NewsResponse
import com.google.common.truth.Truth
import junit.framework.Assert
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class NewsRepoTest {

    private lateinit var newRepo: NewsRepo

    @Mock
    lateinit var newsApi: NewsApi

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        newRepo = NewsRepo(newsApi)
    }

    @Test
    fun mockResponse() {
        runBlocking {
            Mockito.`when`(newsApi.getAllNews()).thenReturn(Response.success(NewsResponse("ok",10)))
            val response = newRepo.getNews()
            org.junit.Assert.assertEquals(response.body()?.status,"ok")
        }
    }
}