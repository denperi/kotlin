package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.movieapp.data.FilterSettings
import com.example.movieapp.viewmodel.MovieViewModel
import com.example.movieapp.data.Movie

@Composable
fun MovieListItem(
    movie: Movie,
    navController: NavController,
    movieViewModel: MovieViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                movieViewModel.selectMovie(movie)
                navController.navigate("movieDetail/${movie.id}")
            }
            .padding(8.dp)
    ) {
        Image(
            painter = rememberAsyncImagePainter(movie.posterUrl),
            contentDescription = movie.title,
            modifier = Modifier.size(80.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Column {
            Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
            Text(text = movie.genre, style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieListScreen(
    navController: NavController,
    movieViewModel: MovieViewModel = hiltViewModel()
) {
    val movies by movieViewModel.filteredMovies.collectAsState()
    val isLoading by movieViewModel.isLoading
    var showFilterDialog by remember { mutableStateOf(false) }
    val filterSettings by movieViewModel.filterSettings.collectAsState()
    val context = LocalContext.current
    val isFilterApplied by movieViewModel.isFilterApplied.collectAsState()

    LaunchedEffect(Unit) {
        movieViewModel.loadMovies(context)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Фильмы") },
                actions = {
                    FilterButtonWithIndicator(
                        showFilterBadge = isFilterApplied,
                        onClick = { showFilterDialog = true }
                    )
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding)) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                movies.isEmpty() -> {
                    Text("Нет доступа к интернету", modifier = Modifier.align(Alignment.Center))
                }
                else -> {
                    LazyColumn {
                        items(movies) { movie ->
                            MovieListItem(movie, navController, movieViewModel)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

        if (showFilterDialog) {
            FilterDialog(
                onApply = { genre, year, russianOnly ->
                    movieViewModel.applyFilters(genre, year, russianOnly)
                    showFilterDialog = false
                    movieViewModel.setFilterApplied(true)
                },
                onReset = {
                    movieViewModel.resetFilters()
                    showFilterDialog = false
                    movieViewModel.setFilterApplied(false)
                          },
                onDismiss = { showFilterDialog = false },
                initialSettings = filterSettings
            )
        }
    }
}

@Composable
fun FilterDialog(
    onApply: (String, Int?, Boolean) -> Unit,
    onReset: () -> Unit,
    onDismiss: () -> Unit,
    initialSettings: FilterSettings
) {
    var genre by remember { mutableStateOf(initialSettings.genre) }
    var year by remember { mutableStateOf(initialSettings.year) }
    var russianOnly by remember { mutableStateOf(initialSettings.russianOnly) }

    AlertDialog(
        onDismissRequest = { onDismiss() },
        title = { Text("Фильтры") },
        text = {
            Column {
                Text("Жанр")
                TextField(
                    value = genre,
                    onValueChange = { genre = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Text("Год")
                TextField(
                    value = year,
                    onValueChange = { year = it },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text("Русские фильмы")
                    Checkbox(
                        checked = russianOnly,
                        onCheckedChange = { russianOnly = it }
                    )
                }
            }
        },
        confirmButton = {
            Button(onClick = {
                val yearInt = year.toIntOrNull()
                onApply(genre, yearInt, russianOnly)
                onDismiss()
            }) {
                Text("Сохранить")
            }
        },
        dismissButton = {
            Button(onClick = {
                onReset()
                onDismiss()
            }) {
                Text("Сбросить")
            }
        }
    )
}

@Composable
fun FilterButtonWithIndicator(showFilterBadge: Boolean, onClick: () -> Unit) {
    Box {
        IconButton(onClick = onClick) {
            Icon(Icons.Outlined.FilterAlt, contentDescription = "Фильтр")
        }
        if (showFilterBadge) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .align(Alignment.TopEnd)
                    .offset(x = (-4).dp, y = 4.dp)
                    .background(Color(0xFFF44336), shape = CircleShape)
            )
        }
    }
}