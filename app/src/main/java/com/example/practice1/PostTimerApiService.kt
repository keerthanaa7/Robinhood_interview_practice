package com.example.practice1

import retrofit2.http.GET

interface PostTimerApiService {

    @GET(Constants.POSTS_ENDPOINT)
    suspend fun getPostTimer(): List<PostItemTimer>
}