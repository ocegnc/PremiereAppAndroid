package com.example.myapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import kotlinx.serialization.Serializable

@Serializable
class Profil

@Composable
fun ProfilScreen(classes: WindowSizeClass) {
    val classeHauteur = classes.windowHeightSizeClass
    val classeLargeur = classes.windowWidthSizeClass
    when (classeLargeur) {
        WindowWidthSizeClass.COMPACT -> /* largeur faible */ {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                MonImage(id = R.drawable.tortue)
                Spacer(modifier = Modifier.height(10.dp))
                Name()
                Spacer(modifier = Modifier.height(20.dp))
                MonTexte()
                Spacer(modifier = Modifier.height(40.dp))
                LogosText()
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        else -> {
            Row (
                Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ){
                Column(
                    modifier= Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    MonImage(id = R.drawable.tortue)
                    Spacer(modifier = Modifier.height(10.dp))
                    Name()
                    Spacer(modifier = Modifier.height(20.dp))
                    MonTexte()
                }
                Column(
                    modifier= Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ){
                    LogosText()
                    Spacer(modifier = Modifier.height(40.dp))
                }
            }
        }
    }
}

@Composable
fun MonImage(id: Int) {
    Image(
        painterResource(id),
        contentDescription = "Tortue",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(150.dp)
            .clip(CircleShape)
    )
}

@Composable
fun Name(){
    Modifier.fillMaxSize()
    Text(
        text = "Océane Guennec",
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Composable
fun MonTexte(){
    Modifier.fillMaxSize()
    Text(
        text = "Etudiante ingénieure informatique",
        style = MaterialTheme.typography.bodyLarge,
    )
    Text(
        text = "Ecole ISIS - INU Champollion",
        fontStyle = FontStyle.Italic
    )
}

@Composable
fun LogosText(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentWidth(Alignment.CenterHorizontally)
    ) {
        //Email
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                imageVector = Icons.Filled.Email,  // Icône Material Design pour l'email
                //painterResource(R.drawable.mail),
                contentDescription = "Email",
                modifier = Modifier
                    .size(20.dp)  // Ajuster la taille de l'icône
            )
            Text(
                modifier = Modifier.padding(10.dp, 0.dp),
                text = "oceane.guennec@gmail.com"
            )
        }
        //Linkedin
        Row(
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Image(
                painterResource(R.drawable.linkedin),
                contentDescription = "linkedin",
                modifier = Modifier.size(30.dp)
            )
            Text(
                modifier = Modifier.padding(5.dp),
                text = "www.linkedin.com/in/oceane_guennec",
            )
        }
    }
}

@Composable
fun Button(){
    androidx.compose.material3.Button(onClick = { /*TODO*/ }) {
        Text(text = "Démarrer")
    }
}