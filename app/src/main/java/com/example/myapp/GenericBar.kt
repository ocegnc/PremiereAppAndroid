//package com.example.myapp
//
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.material3.DockedSearchBar
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.Icon
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.material3.TopAppBar
//import androidx.compose.material3.TopAppBarDefaults
//import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.navigation.NavController
//import androidx.navigation.compose.currentBackStackEntryAsState
//import androidx.navigation.compose.rememberNavController
//import androidx.window.core.layout.WindowSizeClass
//import androidx.window.core.layout.WindowWidthSizeClass
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun TopBar(navController : NavController, searchBar : Boolean ){
//    var text by remember { mutableStateOf("") }
//    var textSearched by remember { mutableStateOf("") }
//    var search by remember { mutableStateOf(false) }
//    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
//    val navController = rememberNavController()
//    val navBackStackEntry by navController.currentBackStackEntryAsState()
//    val currentDestination = navBackStackEntry?.destination
//
//
//    TopAppBar(
//        title = { Text(text = "AlloCinosh",
//            fontSize = 15.sp,
//            textAlign = TextAlign.Center,
//            modifier = Modifier
//                .padding(top = 10.dp, start = 10.dp )) },
//        actions = {
//            if (searchBar) {
//                DockedSearchBar(
//                    query = text,
//                    onQueryChange = {text = it},
//                    onSearch = { search = false; textSearched = it },
//                    active = search,
//                    onActiveChange = {
//                        search = it
//                    },
//                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = "search") },
//                    modifier = Modifier
//                        .width(200.dp)
//                        .height(45.dp)
//                )
//                {
//                }
//            }
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .height(50.dp),
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = MaterialTheme.colorScheme.primaryContainer,
//            titleContentColor = MaterialTheme.colorScheme.primary
//        )
//    )
//
//})
//
//}