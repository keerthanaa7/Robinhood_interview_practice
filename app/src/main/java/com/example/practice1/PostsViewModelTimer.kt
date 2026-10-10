package com.example.practice1

import PostItem
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

sealed interface PostUIStateTimer{
    data object Loading: PostUIStateTimer
    data class Success(val posts: List<PostItemTimer>): PostUIStateTimer
    data class Error(val message: String): PostUIStateTimer
}

class PostsViewModelTimer(private val postTimerRepository: PostTimerRepository) : ViewModel(){

    private val uiStateMutable = MutableStateFlow<PostUIStateTimer>(PostUIStateTimer.Loading)
    val uistate = uiStateMutable.asStateFlow()

    init {
        fetchPosts()
    }
     fun fetchPosts(){
        viewModelScope.launch {
            uiStateMutable.value = PostUIStateTimer.Loading
            try {
                val postslist = postTimerRepository.getPostTimer()
                Log.d("PostsViewModel", "post list ${postslist.size}")
                if(postslist.isEmpty()){
                    uiStateMutable.value = PostUIStateTimer.Error("post list is empty")
                }else{
                    val postWithTimers = postslist.mapIndexed { index, post ->
                        val duration = (index+1) * 10000L
                        post.copy(timeStamp = System.currentTimeMillis() + duration)
                    }
                    uiStateMutable.value = PostUIStateTimer.Success(postWithTimers)
                }
            }catch (e: Exception){
                uiStateMutable.value = PostUIStateTimer.Error("${e}")
            }
        }
    }


}