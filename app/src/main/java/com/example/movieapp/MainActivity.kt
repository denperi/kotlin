package com.example.movieapp

import android.app.Application
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
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.data.MovieViewModelFactory
import com.example.movieapp.data.ProfilePreferences
import com.example.movieapp.viewmodel.ProfileViewModel
import com.example.movieapp.viewmodel.ProfileViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application()

@AndroidEntryPoint
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
    val context = LocalContext.current  /**/

    val viewModel: MovieViewModel = viewModel(factory = MovieViewModelFactory(context))

    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { padding ->
        NavHost(navController, startDestination = "movieList", Modifier.padding(padding)) {
            composable("movieList") {
                MovieListScreen(navController = navController, movieViewModel = viewModel)
            }
            composable("movieDetail/{movieId}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 1
                MovieDetailScreen(movieId, navController, viewModel)
            }
            composable("profile") {
                val context = LocalContext.current
                val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(ProfilePreferences(context)))
                ProfileScreen(navController = navController, viewModel = profileViewModel)
            }
            composable("favorites") {
                FavoritesScreen(movieViewModel = viewModel, navController = navController)
            }
            composable("editProfile") {
                val context = LocalContext.current
                val profileViewModel: ProfileViewModel = viewModel(factory = ProfileViewModelFactory(
                    ProfilePreferences(context)
                ))
                EditProfileScreen(navController = navController, viewModel = profileViewModel)
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    NavigationBar {
        NavigationBarItem(
            icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = "Movies") },
            label = { Text("Фильмы", style = MaterialTheme.typography.bodyMedium) },
            selected = navController.currentDestination?.route == "movieList",
            onClick = {
                navController.navigate("movieList") {
                    popUpTo("movieList") { saveState = true }
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Favorite, contentDescription = "Избранное") },
            label = { Text("Избранное", style = MaterialTheme.typography.bodyMedium) },
            selected = navController.currentDestination?.route == "favorites",
            onClick = {
                navController.navigate("favorites") {
                    popUpTo("favorites") { saveState = true }
                    launchSingleTop = true
                }
            }
        )
        NavigationBarItem(
            icon = { Icon(Icons.Filled.Person, contentDescription = "Профиль") },
            label = { Text("Профиль", style = MaterialTheme.typography.bodyMedium) },
            selected = navController.currentDestination?.route == "profile",
            onClick = {
                navController.navigate("profile")
            }
        )
    }
}