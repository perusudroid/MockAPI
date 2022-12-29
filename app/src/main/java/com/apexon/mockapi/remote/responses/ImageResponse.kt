package com.apexon.mockapi.remote.responses

import com.apexon.mockapi.remote.responses.ImageResult


data class ImageResponse(
    val hits: List<ImageResult>?=null,
    val total: Int,
    val totalHits: Int
)