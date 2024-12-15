package com.example.data.network

import com.example.data.network.response.ElementsResponse
import retrofit2.http.GET

interface Api {

    @GET("/v1/aad8df29-7280-4478-a46d-8f7f4e7c805a")
    suspend fun getData(): ElementsResponse
}