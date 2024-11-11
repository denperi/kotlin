package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.movieapp.viewmodel.MovieViewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}

@Composable
fun MovieApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        NavHost(navController, startDestination = "movieList", Modifier.padding(padding)) {
            composable("movieList") { MovieListScreen(navController) }
            composable("movieDetail/{movieId}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 1
                MovieDetailScreen(movieId, navController)
            }
            composable("profile") { ProfileScreen() }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Movies") },
            label = { Text("Фильмы") },
            selected = navController.currentDestination?.route == "movieList",
            onClick = {
                navController.navigate("movieList") {
                    popUpTo("movieList") { saveState = true }
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Info, contentDescription = "Подробнее") },
            label = { Text("Подробнее") },
            selected = navController.currentDestination?.route == "movieDetail",
            onClick = {
                MovieViewModel.currentMovieId.value?.let { currentMovieId ->
                    navController.navigate("movieDetail/$currentMovieId")
                } ?: run {
                    navController.navigate("movieDetail/1")
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Профиль") },
            label = { Text("Профиль") },
            selected = navController.currentDestination?.route == "profile",
            onClick = {
                navController.navigate("profile")
            }
        )
    }
}