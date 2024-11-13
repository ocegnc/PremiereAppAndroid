package com.example.myapp

import android.media.tv.TvContract.Channels.Logo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.NavigationRailItemColors
import androidx.compose.material3.NavigationRailItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Serializable
class DetailsMovie(val movieId: String)

@Serializable
class DetailsSerie(val serieId: String)

@Serializable
class DetailsActor(val actorId: String)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Screen(viewModel: MainViewModel) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    var text by remember { mutableStateOf("") }
    var search by remember { mutableStateOf(false) }
    var searchBar = true

    val searchSize : Int
    searchSize = when(windowSizeClass.windowWidthSizeClass) {
        WindowWidthSizeClass.COMPACT -> {
            250
        }
        else -> {
            750
        }
    }

    var colorBar = Color(0xFF008080)
    if (currentDestination?.hasRoute<Series>() == true || (currentDestination?.route=="serieDetail/{tvid}") == true){
        colorBar = Color(0xFFFFDF00)
    }
    if (currentDestination?.hasRoute<Acteurs>() == true || (currentDestination?.route=="actorDetail/{personid}") == true){
        colorBar = Color(0xFF22844E)
    }

    Scaffold(
        topBar = { if (currentDestination?.hasRoute<Profil>() != true) {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                ,
                title = { Text(text = "AlloCinosh'",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                   // modifier = Modifier.fillMaxWidth(0.3f)
                ) },
                actions = {
                    if (searchBar) {
                        DockedSearchBar(
                            query = text,
                            onQueryChange = {text = it},
                            onSearch = { search = false;
                                if (currentDestination?.hasRoute<Films>() == true){
                                    viewModel.searchMovie(it)
                                }
                                if (currentDestination?.hasRoute<Series>() == true){
                                    viewModel.searchSerie(it)
                                }
                                if (currentDestination?.hasRoute<Acteurs>() == true){
                                    viewModel.searchActor(it)
                                }
                            },
                            active = search,
                            onActiveChange = {
                                search = it
                            },
                            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search") },
                            trailingIcon = { IconButton(onClick = {
                                text = "";
                                if (currentDestination?.hasRoute<Films>() == true){
                                    viewModel.getMovies()
                                }
                                if (currentDestination?.hasRoute<Series>() == true){
                                    viewModel.getTv()
                                }
                                if (currentDestination?.hasRoute<Acteurs>() == true){
                                    viewModel.getActors()
                                }}) {
                                Icon(
                                    imageVector = Icons.Default.Clear,
                                    contentDescription = "Clear",
                                    tint = Color.Black
                                )
                            } },
                            modifier = Modifier
                                .height(50.dp)
                                .width(searchSize.dp)
                        )
                        {
                        }
                    }
                },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = colorBar,
                    titleContentColor = Color.Black
                )
            )
        }
        },

        floatingActionButton = {
            if (currentDestination?.hasRoute<Profil>() != true) {
                when (windowSizeClass.windowWidthSizeClass) {
                    WindowWidthSizeClass.COMPACT -> {
                        FloatingActionButton(
                            onClick = { navController.navigateUp() },
                            containerColor = colorBar,
                            elevation = FloatingActionButtonDefaults.elevation(8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Retour",
                                tint = Color.Black
                            )
                        }
                    }
                }
            }
        },
        bottomBar = { if (currentDestination?.hasRoute<Profil>() != true) {
            when (windowSizeClass.windowWidthSizeClass) {
                WindowWidthSizeClass.COMPACT -> {
                    BottomAppBar(
                        containerColor = colorBar,
                        contentColor = Color.Black,
                        modifier = Modifier.padding(bottom = 0.dp)
                    ) {
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.AccountCircle, contentDescription = null) },
                            label = { Text("Mon profil") },
                            selected = currentDestination?.hasRoute<Profil>() == true,
                            onClick = { navController.navigate(Profil()) },
                            colors = getNavigationBarItemColors(
                                currentDestination?.hasRoute<Profil>() == true,
                                Color(0xFF0ABAB5)
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Image(
                                    painterResource(R.drawable.baseline_movie_24),
                                    contentDescription = null
                                )
                            }, label = { Text("Films") },
                            selected = currentDestination?.hasRoute<Films>() == true,
                            onClick = { navController.navigate(Films()) },
                            colors = getNavigationBarItemColors(
                                currentDestination?.hasRoute<Films>() == true,
                                Color(0xFF0ABAB5)
                            )
                        )
                        NavigationBarItem(
                            icon = {
                                Image(
                                    painterResource(R.drawable.baseline_tv_24),
                                    contentDescription = null
                                )
                            }, label = { Text("Series") },
                            selected = currentDestination?.hasRoute<Series>() == true,
                            onClick = { navController.navigate(Series()) },
                            colors = getNavigationBarItemColors(
                                currentDestination?.hasRoute<Series>() == true,
                                Color(0xFFFFFF62)
                            )
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Face, contentDescription = null) },
                            label = { Text("Acteurs") },
                            selected = currentDestination?.hasRoute<Acteurs>() == true,
                            onClick = { navController.navigate(Acteurs()) },
                            colors = getNavigationBarItemColors(
                                currentDestination?.hasRoute<Acteurs>() == true,
                                Color(0xFF72BF67)
                            )
                        )
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Add, contentDescription = null) },
                            label = { Text("Question1") },
                            selected = currentDestination?.hasRoute<Question1>() == true,
                            onClick = { navController.navigate(Question1()) },
                            colors = getNavigationBarItemColors(
                                currentDestination?.hasRoute<Question1>() == true,
                                Color(0xFF72BF67)
                            )
                        )
                    }
                }
            }
        }
        }
    )

    { innerPadding ->
        Row {
            Column(modifier = Modifier.background(Color(0xFF008080))) {
                when(windowSizeClass.windowWidthSizeClass) {
                    WindowWidthSizeClass.COMPACT -> {
                    } else -> {
                    if (currentDestination?.hasRoute<Profil>() != true) {
                        Spacer(modifier = Modifier.height(60.dp))
                        NavigationRail(
                            containerColor = colorBar,
                            contentColor = Color.Black,
                        ) {
                            NavigationRailItem(
                                icon = {
                                    Icon(
                                        Icons.Filled.AccountCircle,
                                        contentDescription = null
                                    )
                                },
                                label = { Text("Mon profil") },
                                selected = currentDestination?.hasRoute<Profil>() == true,
                                onClick = { navController.navigate(Profil()) },
                                colors = getNavigationRailItemColors(
                                    currentDestination?.hasRoute<Profil>() == true,
                                    Color(0xFF0ABAB5)
                                )
                            )
                            NavigationRailItem(
                                icon = {
                                    Image(
                                        painterResource(R.drawable.baseline_movie_24),
                                        contentDescription = null
                                    )
                                },
                                label = { Text("Films") },
                                selected = currentDestination?.hasRoute<Films>() == true,
                                onClick = { navController.navigate(Films()) },
                                colors = getNavigationRailItemColors(
                                    currentDestination?.hasRoute<Films>() == true,
                                    Color(0xFF0ABAB5)
                                )
                            )
                            NavigationRailItem(
                                icon = {
                                    Image(
                                        painterResource(R.drawable.baseline_tv_24),
                                        contentDescription = null
                                    )
                                },
                                label = { Text("Series") },
                                selected = currentDestination?.hasRoute<Series>() == true,
                                onClick = { navController.navigate(Series()) },
                                colors = getNavigationRailItemColors(
                                    currentDestination?.hasRoute<Series>() == true,
                                    Color(0xFFFFFF62)
                                )
                            )
                            NavigationRailItem(
                                icon = { Icon(Icons.Filled.Face, contentDescription = null) },
                                label = { Text("Acteurs") },
                                selected = currentDestination?.hasRoute<Acteurs>() == true,
                                onClick = { navController.navigate(Acteurs()) },
                                colors = getNavigationRailItemColors(
                                    currentDestination?.hasRoute<Acteurs>() == true,
                                    Color(0xFF72BF67)
                                )
                            )
                            NavigationRailItem(
                                icon = { Icon(Icons.Filled.ArrowBack, contentDescription = null) },
                                label = { Text("Retour") },
                                selected = false,
                                onClick = { navController.navigateUp() },
                                colors = getNavigationRailItemColors( false, Color.Transparent)
                            )
                        }
                    }
                }
            }
            }
            Column {
                NavHost(
                    navController, startDestination = Profil(),
                    Modifier.padding(innerPadding)
                ) {
                    composable<Profil> { ProfilScreen(viewModel, navController) }
                    composable<Films> { FilmsScreen(viewModel, navController) }
                    composable<Series> { SeriesScreen(viewModel, navController) }
                    composable<Acteurs> { ActeursScreen(viewModel, navController) }
                    composable<Question1> { Question1Screen(viewModel, navController) }
                    composable("movieDetail/{movieid}") { backStackEntry ->
                        val movieId = backStackEntry.arguments?.getString("movieid") ?: ""
                        DetailsMovie(
                            viewModel = viewModel,
                            navController = navController,
                            movieId = movieId,
                        )
                    }
                    composable("serieDetail/{tvid}") { backStackEntry ->
                        val serieId = backStackEntry.arguments?.getString("tvid") ?: ""
                        DetailsSerie(
                            viewModel = viewModel,
                            navController = navController,
                            serieId = serieId,
                        )
                    }
                    composable("actorDetail/{personid}") { backStackEntry ->
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
    }
}


@Composable
fun getNavigationBarItemColors(isSelected: Boolean, indicatorColor: Color): NavigationBarItemColors {
    return NavigationBarItemDefaults.colors(
        selectedIconColor = Color.Black,
        unselectedIconColor = Color.Black,
        indicatorColor = indicatorColor,
        unselectedTextColor = Color.Black,
        selectedTextColor = Color.Black
    )
}

@Composable
fun getNavigationRailItemColors(isSelected: Boolean, indicatorColor: Color): NavigationRailItemColors {
    return NavigationRailItemDefaults.colors(
        selectedIconColor = Color.Black,
        unselectedIconColor = Color.Black,
        indicatorColor = indicatorColor,
        unselectedTextColor = Color.Black,
        selectedTextColor = Color.Black
    )
}








