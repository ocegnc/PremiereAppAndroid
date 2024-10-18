package com.example.myapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.serialization.Serializable
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.android.volley.toolbox.ImageRequest


@Serializable
class Films

@Composable
fun FilmsScreen(viewModel: MainViewModel, navController: NavController){
    val viewModel : MainViewModel = viewModel()
    val movies by viewModel.movies.collectAsState()

    LaunchedEffect(key1 = true){
        viewModel.getMovies()
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier.padding(start = 20.dp, end = 20.dp, top = 60.dp, bottom = 80.dp)
    ){
        items(movies.results.size) { movie ->
            MovieItem(movie = movie, navController = navController)
        }
    }

//    LazyColumn {
//        items(movies){
//            movie -> Text(text = movie.original_title)
//        }
//    }
}

@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    Card(
        onClick = { navController.navigate("movieDetail/${movie.id}") },
        modifier = Modifier.padding(8.dp)
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(16.dp)) {
            Image(
                painter = rememberAsyncImagePainter(
                    coil.request.ImageRequest.Builder(LocalContext.current)
                        .data("https://image.tmdb.org/t/p/w780${movie.poster_path}")
                        .build()
                ),
                contentDescription = "Image film ${movie.title}",
                modifier = Modifier.size(100.dp)
            )
            Text(text = movie.title, style = MaterialTheme.typography.bodyMedium)
        }
    }
}
