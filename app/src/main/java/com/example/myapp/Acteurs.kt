package com.example.myapp

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import kotlinx.serialization.Serializable

@Serializable
class Acteurs

@Composable
fun ActeursScreen(viewModel: MainViewModel, navController: NavController){
    val viewModel : MainViewModel = viewModel
    val actors by viewModel.actors.collectAsState()

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
        LaunchedEffect(key1 = true) {
            viewModel.getActors()
        }
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            items(actors.results) { actor ->
                ActorItem(actor = actor, navController = navController, "")
            }
        }
    }
}


@Composable
fun ActorItem(actor: Actor, navController: NavController, character : String) {
    Card(
        onClick = { navController.navigate("actorDetail/${actor.id}") },
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF72BF67),
            contentColor = Color.Black
        )
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.padding(10.dp)) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w780${actor.profile_path}",
                contentDescription = "Image film ${actor.name}",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(text = actor.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Composable
fun DetailsActor(viewModel: MainViewModel, navController: NavController, actorId: String) {
    val viewModel: MainViewModel = viewModel()
    val actor by viewModel.actor.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getActorDetails(actorId)
    }

    LazyColumn {

    }

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

