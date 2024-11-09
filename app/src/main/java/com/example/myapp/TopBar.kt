package com.example.myapp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.DockedSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController : NavController, searchBar : Boolean ){
    val viewModel : MainViewModel = viewModel()
    var text by remember { mutableStateOf("") }
    var search by remember { mutableStateOf(false) }
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
        ,
        title = { Text(text = "AlloCinosh'",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
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
                            viewModel.searchMovie(it)
                        }
                        if (currentDestination?.hasRoute<Acteurs>() == true){
                            viewModel.searchMovie(it)
                        }
                       },
                    active = search,
                    onActiveChange = {
                        search = it
                    },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search") },
                    modifier = Modifier
                        .width(250.dp)
                        .height(50.dp)
                )
                {
                }
            }
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color(0xFF008080),
            titleContentColor = Color.Black
        )
    )

}