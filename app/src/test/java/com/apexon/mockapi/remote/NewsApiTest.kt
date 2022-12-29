package com.apexon.mockapi.remote


import com.google.common.truth.Truth
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NewsApiTest {

    private lateinit var service : NewsApi
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))//We will use MockWebServers url
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApi::class.java)
    }

    private fun enqueueMockResponse(
        fileName: String
    ) {
        javaClass.classLoader?.let {
            val inputStream = it.getResourceAsStream(fileName)
            val source = inputStream.source().buffer()
            val mockResponse = MockResponse()
            mockResponse.setBody(source.readString(Charsets.UTF_8))
            server.enqueue(mockResponse)
        }
    }

    @Test
    fun getSearchedResult_sentRequest_receivedExpected() {
        runBlocking {
            // Prepare fake response
            enqueueMockResponse("NewsResponse.json")
            //Send Request to the MockServer
            val responseBody = service.getAllNews().body()
            //Request received by the mock server
            val request = server.takeRequest()
            Truth.assertThat(responseBody).isNotNull()
            Truth.assertThat(request.path).isEqualTo("/top-headlines?country=us&apiKey=a08189e17f724571be0caf50855dcc26")
        }
    }

    @Test
    fun getSearchedResult_receivedResponse_wrongPageSize_shouldFail() {
        runBlocking {
            enqueueMockResponse("NewsResponse.json")
            val responseBody = service.getAllNews().body()
            val photoList = responseBody!!.articles
            Truth.assertThat(photoList?.size).isEqualTo(20)
        }
    }

    @Test
    fun getSearchedResult_receivedResponse_correctContent() {
        runBlocking {
            enqueueMockResponse("NewsResponse.json")
            val responseBody = service.getAllNews().body()
            val articles = responseBody!!.articles
            val article = articles?.get(0)
            Truth.assertThat(article?.author).isEqualTo("PNs")
        }
    }

    @After
    fun tearDown() {
        server.shutdown()
    }

}