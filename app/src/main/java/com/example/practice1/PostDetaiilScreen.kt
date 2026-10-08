package com.example.practice1

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PostDetaiilScreen(posttitle: String?){
    Box(modifier = Modifier.fillMaxSize()){
        Text("post title is ${posttitle}")
    }

}