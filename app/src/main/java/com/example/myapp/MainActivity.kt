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

@Serializable class Profil
@Serializable class Edition

val navController = rememberNavController()
val navBackStackEntry by navController.currentBackStackEntryAsState()
val currentDestination = navBackStackEntry?.destination

Scaffold(
    bottomBar = {
        NavigationBar {
            NavigationBarItem(
                icon = { Icons.Filled.AccountCircle }, label = { Text("Mon profil") },
                selected = currentDestination?.hasRoute<Profil>() == true,
                onClick = { navController.navigate(Profil()) })
            NavigationBarItem(
                icon = { Icons.Filled.Create }, label = { Text("Edition du profil") },
                selected = currentDestination?.hasRoute<Edition>() == true,
                onClick = { navController.navigate(Edition()) })
        }
    })
    { innerPadding ->
        NavHost(navController, startDestination = Profil(),
            Modifier.padding(innerPadding)) {
            composable<Profil> { ProfilScreen() }
            composable<Edition> { EditionScreen() }
        }
    }

@Composable
fun ProfilScreen() {
    Text("Mon profil")
}

@Composable
fun EditionScreen(){
    Text("Edition du profil")
}


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyAppTheme {
                val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
                Screen(windowSizeClass)
            }
        }
    }
}

@Composable
fun Screen(classes: WindowSizeClass) {
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
                Button()
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
                    Button()
                }
            }
        }
    }
}

