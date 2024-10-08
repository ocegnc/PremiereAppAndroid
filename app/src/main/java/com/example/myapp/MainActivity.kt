package com.example.myapp

import android.media.tv.TvContract.Channels.Logo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.example.myapp.ui.theme.MyAppTheme
import kotlinx.serialization.Serializable
import androidx.navigation.NavDestination.Companion.hasRoute


@Serializable
class Profil
@Serializable
class Films
@Serializable
class Acteurs
@Serializable
class Favoris

@Composable
fun Screen() {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle , contentDescription = null)}, label = { Text("Mon profil") },
                    selected = currentDestination?.hasRoute<Profil>() == true,
                    onClick = { navController.navigate(Profil()) })
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Menu , contentDescription = null)}, label = { Text("Films") },
                    selected = currentDestination?.hasRoute<Films>() == true,
                    onClick = { navController.navigate(Films()) })
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Face , contentDescription = null)}, label = { Text("Acteurs") },
                    selected = currentDestination?.hasRoute<Acteurs>() == true,
                    onClick = { navController.navigate(Acteurs()) })
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite , contentDescription = null)}, label = { Text("Favoris") },
                    selected = currentDestination?.hasRoute<Favoris>() == true,
                    onClick = { navController.navigate(Favoris()) })
            }
        })
    { innerPadding ->
        NavHost(navController, startDestination = Profil(),
            Modifier.padding(innerPadding)) {
            composable<Profil> { ProfilScreen(windowSizeClass) }
            composable<Favoris> { FavorisScreen() }
            composable<Films> { FilmsScreen() }
            composable<Acteurs> { ActeursScreen() }
        }
    }
}

@Composable
fun FilmsScreen(){
    Text("Films")
}
@Composable
fun ActeursScreen(){
    Text("Acteurs")
}
@Composable
fun FavorisScreen(){
    Text("Mes favoris")
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                Screen()
            }
        }
    }
}

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
                    modifier=Modifier.padding(16.dp),
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
                    modifier=Modifier.padding(16.dp),
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

