package com.example.myapp

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.serialization.Serializable
import androidx.compose.foundation.lazy.items


@Serializable
class Films

@Composable
fun FilmsScreen(viewModel: MainViewModel){
    val movies by viewModel.movies.collectAsState()

    /*if (movies.isEmpty()) viewModel.getMovies()

    LazyColumn {
        items(movies){
            movie -> Text(text = movie.original_title)
        }
    }*/
}