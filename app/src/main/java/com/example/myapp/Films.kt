package com.example.myapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.serialization.Serializable
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest


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
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 20.dp, end = 20.dp)
    ){
        items(movies.results) { movie ->
            MovieItem(movie = movie, navController = navController)
        }
    }
}


@Composable
fun MovieItem(movie: Movie, navController: NavController) {
    Card(
        onClick = { navController.navigate("movieDetail/${movie.id}") },
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0ABAB5),
            contentColor = Color.Black
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                    model = "https://image.tmdb.org/t/p/w780${movie.poster_path}",
                    contentDescription = "Image film ${movie.title}",
                    modifier = Modifier
                        .fillMaxWidth()
            )
            Text(text = movie.title, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DetailsScreen(viewModel: MainViewModel, navController: NavController, movieId: String) {
    val viewModel: MainViewModel = viewModel()
    val movie by viewModel.movie.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getMovieDetails(movieId)
    }

    var genreNames = ""
    for(genre in movie.genre_ids)
    {
        genreNames += genre.name + " "
    }
}
