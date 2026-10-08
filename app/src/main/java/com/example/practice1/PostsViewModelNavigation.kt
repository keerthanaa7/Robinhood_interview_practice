package com.example.practice1

import PostItem
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface PostUIStateNav{
    data object Loading: PostUIStateNav
    data class Success(val posts: List<PostItem>): PostUIStateNav
    data class Error(val message: String): PostUIStateNav
}

class PostsViewModelNav(private val postRepository: PostRepository) : ViewModel(){

    private val uiStateMutable = MutableStateFlow<PostUIStateNav>(PostUIStateNav.Loading)
    val UIstate = uiStateMutable.asStateFlow()

    init {
        fetchPosts()
    }
     fun fetchPosts(){
        viewModelScope.launch {
            uiStateMutable.value = PostUIStateNav.Loading
            try {
                val postslist = postRepository.getPosts()
                Log.d("PostsViewModel", "post list ${postslist.size}")
                if(postslist.isEmpty()){
                    uiStateMutable.value = PostUIStateNav.Error("post list is empty")
                }else{
                    uiStateMutable.value = PostUIStateNav.Success(postslist)
                }
            }catch (e: Exception){
                uiStateMutable.value = PostUIStateNav.Error("${e}")
            }
        }
    }
}