//package com.example.myapp
//
//package com.example.myapp
//
//import androidx.compose.foundation.layout.*
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Search
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.ui.text.input.ImeAction
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SearchTopBar(onSearchQueryChange: (String) -> Unit) {
//    var searchQuery by remember { mutableStateOf(TextFieldValue("")) }
//
//    TopAppBar(
//        title = {
//            Text(
//                text = "Search App",
//                style = MaterialTheme.typography.titleLarge
//            )
//        },
//        actions = {
//            // Icône de recherche
//            IconButton(onClick = { /* Ici tu peux ajouter la logique de recherche */ }) {
//                Icon(Icons.Filled.Search, contentDescription = "Search")
//            }
//
//            // Champ de texte pour la recherche
//            OutlinedTextField(
//                value = searchQuery,
//                onValueChange = { query ->
//                    searchQuery = query
//                    onSearchQueryChange(query.text) // Appeler le callback pour passer la valeur de recherche
//                },
//                label = { Text("Search...") },
//                singleLine = true,
//                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
//                modifier = Modifier
//                    .width(200.dp)  // Limiter la taille du champ de recherche
//                    .padding(end = 8.dp)
//            )
//        },
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(8.dp),
//        colors = TopAppBarDefaults.mediumTopAppBarColors(
//            containerColor = Color(0xFF0ABAB5), // Couleur de fond de la top bar
//            titleContentColor = Color.White
//        )
//    )
//}
//
//@Composable
//fun SearchScreen() {
//    var searchQuery by remember { mutableStateOf("") }
//
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp)
//    ) {
//        // Appel de la Top App Bar avec la fonction de recherche
//        SearchTopBar(onSearchQueryChange = { query ->
//            searchQuery = query
//        })
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Afficher la recherche en cours ou les résultats
//        Text(text = "Recherche : $searchQuery")
//        // Ajouter ici ta logique pour afficher les résultats filtrés
//    }
//}
//
//@Composable
//@Preview(showBackground = true)
//fun PreviewSearchScreen() {
//    MaterialTheme {
//        SearchScreen()
//    }
//}
