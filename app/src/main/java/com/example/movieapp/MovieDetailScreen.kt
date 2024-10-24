package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel()
) {
    val movie = movieViewModel.movies.value?.firstOrNull { it.id == movieId }

    if (movie != null) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            item {
                IconButton(onClick = { navController.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Назад")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Image(
                    painter = painterResource(id = movie.posterResId),
                    contentDescription = movie.title,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${movie.title}", style = MaterialTheme.typography.titleLarge,fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Год: ${movie.premiere}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Жанр: ${movie.genre}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Страна: ${movie.country}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Описание: ${movie.description}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Режиссер: ${movie.director}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Актеры: ${movie.starring}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "Длительность: ${movie.duration}", style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}