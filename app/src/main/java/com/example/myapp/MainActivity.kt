package com.example.myapp

import android.media.tv.TvContract.Channels.Logo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale

@Serializable
class DetailsMovie(val movieId: String)

@Serializable
class DetailsSerie(val serieId: String)

@Serializable
class DetailsActor(val actorId: String)

@Composable
fun Screen(viewModel: MainViewModel) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    Scaffold(
        bottomBar = {
            BottomAppBar(containerColor = Color(0xFF008080), contentColor = Color.Black) {
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle , contentDescription = null)}, label = { Text("Mon profil") },
                    selected = currentDestination?.hasRoute<Profil>() == true,
                    onClick = { navController.navigate(Profil()) },
                    colors = getNavigationBarItemColors(currentDestination?.hasRoute<Profil>() == true)
                )
                NavigationBarItem(
                    icon = { Image( painterResource(R.drawable.baseline_movie_24) , contentDescription = null) }, label = { Text("Films") },
                    selected = currentDestination?.hasRoute<Films>() == true,
                    onClick = { navController.navigate(Films()) },
                    colors = getNavigationBarItemColors(currentDestination?.hasRoute<Profil>() == true)
                )
                NavigationBarItem(
                    icon = { Image( painterResource(R.drawable.baseline_tv_24) , contentDescription = null)}, label = { Text("Series") },
                    selected = currentDestination?.hasRoute<Series>() == true,
                    onClick = { navController.navigate(Series()) },
                    colors = getNavigationBarItemColors(currentDestination?.hasRoute<Profil>() == true)
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Face , contentDescription = null)}, label = { Text("Acteurs") },
                    selected = currentDestination?.hasRoute<Acteurs>() == true,
                    onClick = { navController.navigate(Acteurs()) },
                    colors = getNavigationBarItemColors(currentDestination?.hasRoute<Profil>() == true)
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Favorite , contentDescription = null)}, label = { Text("Favoris") },
                    colors = getNavigationBarItemColors(currentDestination?.hasRoute<Profil>() == true),
                    selected = currentDestination?.hasRoute<Favoris>() == true,
                    onClick = { navController.navigate(Favoris()) })
            }
        }
    )

    { innerPadding ->
        NavHost(navController, startDestination = Profil(),
            Modifier.padding(innerPadding)) {
            composable<Profil> { ProfilScreen(windowSizeClass) }
            composable<Favoris> { FavorisScreen() }
            composable<Films> { FilmsScreen(viewModel, navController) }
            composable<Series> { SeriesScreen(viewModel, navController) }
            composable<Acteurs> { ActeursScreen(viewModel, navController) }
            composable("movieDetail/{movieid}"){ backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieid") ?: ""
                DetailsMovie(
                    viewModel = viewModel,
                    navController = navController,
                    movieId = movieId,
                )
            }
            composable("serieDetail/{tvid}"){ backStackEntry ->
                val serieId = backStackEntry.arguments?.getString("tvid") ?: ""
                DetailsSerie(
                    viewModel = viewModel,
                    navController = navController,
                    serieId = serieId,
                )
            }
            composable("actorDetail/{personid}"){ backStackEntry ->
                val actorId = backStackEntry.arguments?.getString("personid") ?: ""
                DetailsActor(
                    viewModel = viewModel,
                    navController = navController,
                    actorId = actorId,
                )
            }
        }
    }
}


@Composable
fun getNavigationBarItemColors(isSelected: Boolean): NavigationBarItemColors {
    return NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        unselectedIconColor = Color.Black,
        indicatorColor = Color(0xFF0ABAB5),
        unselectedTextColor = Color.Black,
        selectedTextColor = Color.Black
    )
}



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val viewmodel : MainViewModel by viewModels()

        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                Screen(viewmodel)
            }
        }
    }
}



