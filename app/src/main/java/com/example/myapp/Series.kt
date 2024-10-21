package com.example.myapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable

@Serializable
class Series

@Composable
fun SeriesScreen(viewModel: MainViewModel, navController: NavController){
    val viewModel : MainViewModel = viewModel
    val tv by viewModel.tv.collectAsState()

    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            tonalElevation = 30.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.sunflowers),
                contentDescription = "background étoilée",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        LaunchedEffect(key1 = true) {
            viewModel.getTv()
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            items(tv.results) { serie ->
                TvItem(serie = serie, navController = navController)
            }
        }
    }
}


@Composable
fun TvItem(serie: Serie, navController: NavController) {
    Card(
        onClick = { navController.navigate("serieDetail/${serie.id}") },
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF0ABAB5),
            contentColor = Color.Black
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w780${serie.poster_path}",
                contentDescription = "Image film ${serie.name}",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(text = serie.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

//@Composable
//fun DetailsSerie(viewModel: MainViewModel, navController: NavController, serieId: String) {
//    val viewModel: MainViewModel = viewModel()
//    val serie by viewModel.serie.collectAsState()
//
//    LaunchedEffect(key1 = true) {
//        viewModel.getSerieDetails(serieId)
//    }
//
//    var genreNames = ""
//    for(genre in serie.genre_ids)
//    {
//        genreNames += genre.name + " "
//    }
//
//
//}
