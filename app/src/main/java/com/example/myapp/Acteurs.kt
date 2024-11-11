package com.example.myapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import androidx.window.core.layout.WindowWidthSizeClass
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
import java.util.Locale

@Serializable
class Acteurs

@Composable
fun ActeursScreen(viewModel: MainViewModel, navController: NavController){
    val actors by viewModel.actors.collectAsState()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass


    LaunchedEffect(key1 = true) {
        viewModel.getActors()
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            tonalElevation = 30.dp
        ) {
            Image(
                painter = painterResource(id = R.drawable.cafe),
                contentDescription = "background étoilée",
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }
        when(windowSizeClass.windowWidthSizeClass) {
            WindowWidthSizeClass.COMPACT -> {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            items(actors.results) { actor ->
                ActorItem(actor = actor, navController = navController, "", false)
            }
        }
            } else -> {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                items(actors.results) { actor ->
                    ActorItem(actor = actor, navController = navController, "", false)
                }
            }
        }
        }
    }
}


@Composable
fun ActorItem(actor: Actor, navController: NavController, character : String, isDetailPage : Boolean) {
    Card(
        onClick = { navController.navigate("actorDetail/${actor.id}") },
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isDetailPage) Color.Transparent else Color(0xFF72BF67),
            contentColor = Color.Black
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w780${actor.profile_path}",
                contentScale = ContentScale.Crop,
                contentDescription = "Image film ${actor.name}",
                modifier = if (isDetailPage){Modifier
                    .clip(CircleShape)
                    .size(100.dp)} else { Modifier.fillMaxWidth() }
            )
            Text(text = actor.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DetailsActor(viewModel: MainViewModel, navController: NavController, actorId: String) {
    val viewModel: MainViewModel = viewModel()
    val actor by viewModel.actor.collectAsState()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    LaunchedEffect(key1 = true) {
        viewModel.getActorDetails(actorId)
    }

    when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(top = 60.dp)
            ) {
                item {
                    Text(
                        text = (actor.name),
                        fontSize = 30.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Start
                    ) {
                        PhotoActor(actor)
                        InfoActor(actor)
                    }
                }
                item {
                    Biographie(actor)
                }
            }
        }else ->{
        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(top = 20.dp)
        ) {
            item {
                Text(
                    text = (actor.name),
                    fontSize = 30.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }
            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PhotoActor(actor)
                    InfoActor(actor)
                }
            }
            item {
                Biographie(actor)
            }
            }
        }
    }
}

@Composable
fun PhotoActor(actor: Actor){

        AsyncImage(
            model = "https://image.tmdb.org/t/p/w780${actor.profile_path}",
            contentDescription = "Image film ${actor.name}",
            modifier = Modifier
                .size(200.dp)
                .fillMaxWidth(0.4f)
        )
    }

@Composable
fun InfoActor(actor: Actor) {
    Column(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth(0.6f),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
        val formattedDate = try {
            val date = dateFormat.parse(actor.birthday)
            SimpleDateFormat("d MMMM yyyy", Locale.FRENCH).format(date)
        } catch (e: Exception) {
            actor.birthday
        }
        Text(
            text = "Date de naissance : $formattedDate",
            fontSize = 13.sp,
            color = Color.Black,
        )
        Text(
            text = "Lieu de naissance : ${actor.place_of_birth}",
            fontSize = 13.sp,
            color = Color.Black,
        )
    }
}

@Composable
fun Biographie(actor: Actor){
    Text(
        text = "Biographie",
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        modifier = Modifier.padding(start = 10.dp, top = 20.dp),
        color = Color.Black,
        )
    Text(
        text = actor.biography.ifEmpty { "Aucune biographie disponible." },
        fontSize = 15.sp,
        color = Color.Black,
        modifier = Modifier.padding(start = 10.dp, top = 10.dp),
        textAlign = TextAlign.Justify
    )
}

fun castToActor(cast: Cast): Actor {
    return Actor(
        id = cast.id.toString(),
        name = cast.name,
        biography = "",
        gender = cast.gender.toString(),
        profile_path = cast.profile_path ?: "",
        popularity = cast.popularity
    )
}

@Composable
fun Casting(castlist: List<Cast>, navController: NavController){
    Column {
        Text(
            text = "CASTING",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
        )

        LazyRow() {
            items(castlist.take(15)) { cast ->
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
}

