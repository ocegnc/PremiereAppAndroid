package com.example.myapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale


@Serializable
class Series

@Composable
fun SeriesScreen(viewModel: MainViewModel, navController: NavController, searchQuery: String){
    val viewModel : MainViewModel = viewModel
    val tv by viewModel.tv.collectAsState()

    if(searchQuery != "")
    {
        LaunchedEffect(key1 = true){
            viewModel.searchSerie(searchQuery)
        }
    }
    else
    {
        LaunchedEffect(key1 = true) {
            viewModel.getTv()
        }
    }

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
            containerColor = Color(0xFFFFDF00),
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

@Composable
fun DetailsSerie(viewModel: MainViewModel, navController: NavController, serieId: String) {
    val viewModel: MainViewModel = viewModel()
    val serie by viewModel.serie.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getSerieDetails(serieId)
    }

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(bottom = 80.dp)
    ){
        item{ Titre(serie) }
        item{ InfoSerie(serie) }
        item { Synopsis(serie) }
        item { Casting(serie, navController) }
    }
}

@Composable
fun Titre(serie : Serie){
    var genreNames = ""
    for ((index, genre) in serie.genres.withIndex()) {
        genreNames += genre.name
        if (index < serie.genres.size - 1) {
            genreNames += " & "
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780${serie.backdrop_path}",
            contentDescription = "Image serie ${serie.name}",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 15.dp)
        )
        Text(
            text = (serie.name),
            fontSize = 30.sp,
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
        Text(text = genreNames, fontStyle = FontStyle.Italic)
    }
}

@Composable
fun InfoSerie(serie: Serie) {
    Row(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780${serie.poster_path}",
            contentDescription = "Image film ${serie.name}",
            modifier = Modifier
                .size(200.dp)
        )
        Column(modifier = Modifier
            .padding(end = 10.dp)
            .fillMaxWidth()
        ) {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
            val formattedDate = try {
                val date = dateFormat.parse(serie.first_air_date)
                SimpleDateFormat("d MMMM yyyy", Locale.FRENCH).format(date)
            } catch (e: Exception) {
                serie.first_air_date
            }
            Text(text = "Depuis le $formattedDate")
            val director = if (serie.created_by.isNotEmpty()) { serie.created_by.joinToString(", ") { it.name }
                            } else { "Inconnu" }
            Text(text = "Créée par $director")
            Text(text = "Saison ${serie.number_of_seasons}")
            val countries = serie.production_countries.joinToString(", ") { it.name }
            Text(text = "Pays de production : $countries")
        }
    }
}

@Composable
fun Synopsis(serie: Serie) {
    Column(
        modifier = Modifier
            .padding(top = 10.dp)
            .fillMaxWidth()
    ) {
        Text(
            text = "SYNOPSIS",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            modifier = Modifier.padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
        )
        Text(
            text = serie.overview.ifEmpty { "Aucun résumé disponible." },
            textAlign = TextAlign.Justify,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp)
        )
    }
}

@Composable
fun Casting(serie: Serie, navController: NavController){
    Column {
        Text(
            text = "CASTING",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )
    }
    LazyRow {
        items(serie.credits.cast.take(15)) { cast ->
            val actor = castToActor(cast)
            ActorItem(
                actor = actor,
                navController = navController,
                character = cast.character,
                isDetailPage = true
            )
        }
    }
}

