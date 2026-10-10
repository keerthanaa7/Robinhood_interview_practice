package com.example.practice1

class PostTimerRepository {
    suspend fun getPostTimer(): List<PostItemTimer>{
        return RetrofitInstance.postTimerApiService.getPostTimer()
    }
}