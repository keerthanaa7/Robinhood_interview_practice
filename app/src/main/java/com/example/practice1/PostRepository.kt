package com.example.practice1

import PostItem

class PostRepository {

    suspend fun getPosts(): List<PostItem>{
        return RetrofitInstance.postApiService.getPost()
    }
}