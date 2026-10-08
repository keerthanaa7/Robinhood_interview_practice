package com.example.practice1

import PostItem
import com.example.practice1.Constants.POSTS_ENDPOINT
import retrofit2.http.GET

interface PostApiService {

    @GET(POSTS_ENDPOINT)
    suspend fun getPost(): List<PostItem>
}