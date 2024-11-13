package com.example.myapp

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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapp.Collection
import kotlinx.serialization.Serializable

@Serializable
class Question1

@Composable
fun Question1Screen(viewModel: MainViewModel, navController: NavController) {
    val collections by viewModel.collections.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.getCollection()
    }

    Box(modifier = Modifier.fillMaxWidth()) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 20.dp, end = 20.dp)
        ) {
            items(collections.results) { collection ->
                CollectionItem(collection = collection)
            }
        }
    }
}

@Composable
fun CollectionItem(collection: Collection) {
    Card(
        modifier = Modifier.padding(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xFF72BF67),
            contentColor = Color.Black
        )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(10.dp)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w780${collection.poster_path}",
                contentDescription = "Image film ${collection.name}",
                modifier = Modifier
                    .fillMaxWidth()
            )
            Text(text = collection.name, style = MaterialTheme.typography.bodyMedium)
        }
    }
}