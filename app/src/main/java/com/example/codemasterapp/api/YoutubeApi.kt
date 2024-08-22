package com.example.codemasterapp.api

import com.example.codemasterapp.models.YoutubeVideoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface YoutubeApi {
    @GET("videos")
    suspend fun getVideoDetails(
        @Query("part") part: String,
        @Query("id") videoId: String,
        @Query("key") apiKey: String
    ): Response<YoutubeVideoResponse>
}