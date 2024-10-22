package com.example.myapp

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Locale


@Serializable
class Films

@Composable
fun FilmsScreen(viewModel: MainViewModel, navController: NavController){
    val viewModel : MainViewModel = viewModel()
    val movies by viewModel.movies.collectAsState()

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
        LaunchedEffect(key1 = true) {
            viewModel.getMovies()
        }
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

    LaunchedEffect(key1 = true) {
        viewModel.getMovieDetails(movieId)
    }

    var genreNames = ""
    for(genre in movie.genres)
    {
        genreNames += genre.name + "&"
    }

//    val date = movie.release_date
//    val newDate = SimpleDateFormat("dd MMM yyyy")
//    if (date== android.text.format.DateFormat("yyyy-MM-dd") {
//            date = newDate
//        }
//    else{
//    }


    LazyColumn (
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 60.dp, bottom = 80.dp)
    ) {
        item {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = (movie.original_title),
                    fontSize = 30.sp,
                    color = MaterialTheme.colorScheme.primary,
                    //fontStyle = ,
                    fontWeight = FontWeight.Bold
                )
                Text(text = movie.release_date)
                Text(text = genreNames)
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w780${movie.backdrop_path}",
                    contentDescription = "Image film ${movie.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        item {
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w780${movie.poster_path}",
                    contentDescription = "Image film ${movie.title}",
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
        item { 
            Column {
                Text(text = "Synopsis")
                Text(text = movie.overview)
            }
        }
        item {
            Column {
                Text(text = "A l'affiche ...")
            }
            LazyRow {

            }
        }
    }
}









//Image(
//painter = rememberAsyncImagePainter(
//ImageRequest.Builder(LocalContext.current)
//.data(data = "https://image.tmdb.org/t/p/w1280" + movie.backdrop_path)
//.apply(block = fun ImageRequest.Builder.() {
//    crossfade(true)
//    size(
//        1280,
//        1000
//    )
//}).build()
//),
//contentDescription = "Image film ${movie.title}",
//modifier = Modifier
//.fillMaxWidth()
//.padding(start = 5.dp, end = 5.dp)
//)
//}
//}
//item{
//    Row(
//        modifier = Modifier
//            .padding(top = 5.dp)
//            .fillMaxWidth(),
//        horizontalArrangement = Arrangement.spacedBy(50.dp),
//        verticalAlignment = Alignment.CenterVertically
//    ){
//        Image(
//            painter = rememberAsyncImagePainter(
//                ImageRequest.Builder(LocalContext.current)
//                    .data(data = "https://image.tmdb.org/t/p/w780" + movie.poster_path)
//                    .apply(block = fun ImageRequest.Builder.() {
//                        crossfade(true)
//                        size(
//                            200,
//                            200
//                        )
//                    }).build()
//            ),
//            contentDescription = "Image film ${movie.title}",
//            modifier = Modifier.size(200.dp)
//        )
//        Column {
//            Text(text = formatDate(movie.release_date), fontSize = 15.sp,color = MaterialTheme.colorScheme.primary, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
//            Text(text = genreNames, fontSize = 15.sp,color = MaterialTheme.colorScheme.primary, fontStyle = androidx.compose.ui.text.font.FontStyle.Italic)
//        }
//    }
//}
//item{
//    Column {
//        Text(text = "Synopsis", fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 15.dp))
//        Text(text = movie.overview, fontSize = 12.sp, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp))
//    }
//}
//item{
//    Text(text = "Têtes d'affiche", fontSize = 20.sp, fontWeight = androidx.compose.ui.text.font.FontWeight.Bold, color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(start = 20.dp))
//    LazyRow {
//        items(movie.credits.cast.take(15)){
//                cast ->
//            val actor = castToActor(cast)
//            ActorBox(
//                actor = actor,
//                windowSizeClass = windowSizeClass,
//                navController = navController,
//                character = cast.character
//            )
//        }
//    }
//}
//}