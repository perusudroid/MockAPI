package com.apexon.mockapi.vm

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.apexon.mockapi.getOrAwaitValue
import com.apexon.mockapi.remote.responses.NewsResponse
import com.apexon.mockapi.repo.NewsRepo
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.asCoroutineDispatcher
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.*
import org.junit.Assert.*

import org.junit.rules.TestRule
import org.mockito.Mockito
import retrofit2.Response
import java.util.concurrent.Executors

@ExperimentalCoroutinesApi
class NewsViewModelTest {

    @Rule
    @JvmField
    var rule: TestRule = InstantTaskExecutorRule()

    private val mainThreadSurrogate = Executors.newSingleThreadExecutor().asCoroutineDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        mainThreadSurrogate.close()
    }

    @Test
    fun test() {

        val newsRepo = mockk<NewsRepo>()
        coEvery {
            newsRepo.getNews()
        } returns Response.success(NewsResponse("ok",10))

        val mainViewModel = NewsViewModel(newsRepo)
        mainViewModel.getNews()
        val response = mainViewModel.news.getOrAwaitValue()
        //assertNotNull(response)
        assertEquals("ok", response.data?.status)
    }
}