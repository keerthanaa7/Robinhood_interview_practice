package com.example.practice1

import PostItem
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun PostHomeScreen(postsViewModelNav: PostsViewModelNav, onPostClick:(String) -> Unit){
    val uistate by postsViewModelNav.UIstate.collectAsStateWithLifecycle()
    Box(modifier = Modifier.fillMaxSize()){
        when(val state = uistate){
            is PostUIStateNav.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            is PostUIStateNav.Success-> LoadPosts(state.posts, onPostClick)
            is PostUIStateNav.Error -> Text(text = "error ${state.message}", modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun LoadPosts(postlist: List<PostItem>,onPostClick:(String) -> Unit ){
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(postlist, key = {it.id}){post ->
            DisplayEachItem(post, onPostClick)
            HorizontalDivider(thickness = 1.dp)
        }
    }
}

@Composable
fun DisplayEachItem(post :PostItem,onPostClick:(String) -> Unit  ){
    Row(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize().clickable{onPostClick(post.title)}) {
            Text(text = "post ${post.title}")
            Text(text = "post ${post.body}")
        }

    }
}