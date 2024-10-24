package com.example.movieapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.*
import com.example.movieapp.viewmodel.MovieViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.List
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MovieApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                MovieDetailScreen(movieId)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.Filled.List, contentDescription = "Movies") },
            label = { Text("Фильмы") },
            selected = true,
            onClick = {
                navController.navigate("movieList") {
                    popUpTo("movieList") { saveState = true }
                    launchSingleTop = true
                }
            }
        )
        // Другие элементы навигации можно добавить здесь
    }
}