package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun MovieListScreen(
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel() // Подключаем ViewModel
) {
    val movies = movieViewModel.movies.value ?: emptyList() // Получаем список из ViewModel

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(movies.size) { index ->
            val movie = movies[index]
            MovieListItem(movie, navController, movieViewModel)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun MovieListItem(
    movie: com.example.movieapp.data.Movie,
    navController: NavController,
    movieViewModel: MovieViewModel // Передаем ViewModel для выбора фильма
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                movieViewModel.selectMovie(movie) // Устанавливаем выбранный фильм
                navController.navigate("movieDetail/${movie.id}")
            }
            .padding(8.dp)
    ) {
        Image(
            painter = painterResource(id = movie.posterResId),
            contentDescription = movie.title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = movie.title, style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Text(text = movie.genre, style = androidx.compose.material3.MaterialTheme.typography.bodySmall)
        }
    }
}