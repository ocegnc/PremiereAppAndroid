package com.example.myapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import kotlinx.serialization.Serializable
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale


@Serializable
class Films

@Composable
fun FilmsScreen(viewModel: MainViewModel, navController: NavController) {
    val movies by viewModel.movies.collectAsState()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    LaunchedEffect(key1 = true) {
        viewModel.getMovies()
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            tonalElevation = 30.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.nuit_etoilee),
                contentDescription = "background étoilée",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        when (windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    items(movies.results) { movie ->
                        MovieItem(movie = movie, navController = navController)
                    }
                }
            }

            else -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 20.dp, end = 20.dp)
                ) {
                    items(movies.results) { movie ->
                        MovieItem(movie = movie, navController = navController)
                    }
                }
            }
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
fun DetailsMovie(viewModel: MainViewModel, navController: NavController, movieId: String) {
    val viewModel: MainViewModel = viewModel()
    val movie by viewModel.movie.collectAsState()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    LaunchedEffect(key1 = true) {
        viewModel.getMovieDetails(movieId)
    }

    when (windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                item { Photo(movie) }
                item { Titre(movie) }
                item { InfoMovie(movie) }
                item { Synopsis(movie) }
                item { Casting(movie.credits.cast, navController) }
            }
        }else ->{
                LazyColumn(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    item {
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                        Titre(movie)
                        Spacer(modifier = Modifier.padding(top = 10.dp))
                    }
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        )
                        {
                            InfoMovie(movie)
                            Photo(movie)
                        }
                    }
                    item { Synopsis(movie) }
                    item { Casting(movie.credits.cast, navController) }
                }
            }
        }
    }


@Composable
fun Photo(movie: Movie){
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w780${movie.backdrop_path}",
        contentDescription = "Image film ${movie.title}",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 15.dp)
    )
}

@Composable
fun Titre(movie: Movie){
    var genreNames = ""
    for ((index, genre) in movie.genres.withIndex()) {
        genreNames += genre.name
        if (index < movie.genres.size - 1) {
            genreNames += " & "
        }
    }
    Text(
        text = (movie.original_title),
        fontSize = 30.sp,
        color = Color.Black,
        fontWeight = FontWeight.Bold
    )
    Text(text = genreNames, fontStyle = FontStyle.Italic)


}

@Composable
fun InfoMovie(movie: Movie){
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780${movie.poster_path}",
            contentDescription = "Image film ${movie.title}",
            modifier = Modifier
                .size(200.dp)
        )
        Column(modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth()
        ) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
            val formattedDate = try {
                val date = dateFormat.parse(movie.release_date)
                SimpleDateFormat("d MMMM yyyy", Locale.FRENCH).format(date)
            } catch (e: Exception) {
                movie.release_date
            }
            Text(text = "Sorti le $formattedDate")
            val director = movie.credits.crew.firstOrNull { it.job == "Director" }?.name ?: "Inconnu"
            Text(text = "Créé par $director")
            Text(text = "Durée : ${movie.runtime} min")
            val countries = movie.production_countries.joinToString(", ") { it.name }
            Text(text = "Pays de production : $countries")
        }
    }
}

@Composable
fun Synopsis(movie: Movie){
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "SYNOPSIS",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom =10.dp)
        )
        Text(
            text = movie.overview.ifEmpty { "Aucun résumé disponible." },
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        )
    }
}
