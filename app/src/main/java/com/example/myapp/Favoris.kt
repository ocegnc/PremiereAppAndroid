package com.example.myapp

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import kotlinx.serialization.Serializable

@Serializable
class Favoris

@Composable
fun FavorisScreen(){
    Text("Mes favoris")
}