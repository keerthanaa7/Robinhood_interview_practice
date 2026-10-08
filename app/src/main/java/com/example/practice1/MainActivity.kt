package com.example.practice1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.room.util.TableInfo
import com.example.practice1.ui.theme.Practice1Theme

class MainActivity : ComponentActivity() {

    private val postRepository: PostRepository by lazy {
        PostRepository()
    }

    private val postviewmodel: PostsViewModel by viewModels {
        viewModelFactory {
            initializer {
                PostsViewModel(postRepository)
            }
        }
    }

    private val postsViewModelNav: PostsViewModelNav by viewModels {
        viewModelFactory {
            initializer {
                PostsViewModelNav(postRepository)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
           // SearchDebounce(postviewmodel)
            Navigation(postsViewModelNav)
        }
    }
}

@Composable
fun Navigation(postsViewModelNav: PostsViewModelNav){
    val navcontroller = rememberNavController()
    NavHost(navController = navcontroller, startDestination = "post home screen") {
       composable(route = "post home screen"){
           PostHomeScreen(postsViewModelNav) {
               post -> navcontroller.navigate("postdetailscreen/${post}")
           }
       }

        composable(route = "postdetailscreen/{post}", arguments = listOf(navArgument("post"){type=
            NavType.StringType})){
            backstackentry ->
            val post = backstackentry.arguments?.get("post")
            PostDetaiilScreen(post as String)
        }
    }

}

@Composable
fun SearchDebounce(postsViewModel: PostsViewModel) {
    val search by postsViewModel.searchQuery.collectAsStateWithLifecycle()
    val uistate by postsViewModel.uistate.collectAsStateWithLifecycle()
    Column(modifier = Modifier.fillMaxSize()) {
        TextField(value = search, onValueChange = { postsViewModel.onQueryChanged(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)) {
            when (val state = uistate) {
                is PostUIState.Loading -> CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                is PostUIState.Success ->
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.posts) { post ->
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text(text = "post title ${post.title}")
                            }
                        }
                    }
                is PostUIState.Error -> Text("error ${state.message}", modifier = Modifier.align(Alignment.Center))
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Practice1Theme {
        Greeting("Android")
    }
}