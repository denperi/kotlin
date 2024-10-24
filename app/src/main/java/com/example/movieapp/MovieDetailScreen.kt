package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.movieapp.viewmodel.MovieViewModel

@Composable
fun MovieDetailScreen(
    movieId: Int,
    movieViewModel: MovieViewModel = viewModel() // Подключаем ViewModel
) {
    val movie = movieViewModel.movies.value?.firstOrNull { it.id == movieId }

    if (movie != null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Image(
                painter = painterResource(id = movie.posterResId),
                contentDescription = movie.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "Title: ${movie.title}", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
            Text(text = "Genre: ${movie.genre}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(text = "Description: ${movie.description}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(text = "Director: ${movie.director}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(text = "Starring: ${movie.starring}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
            Text(text = "Duration: ${movie.duration}", style = androidx.compose.material3.MaterialTheme.typography.bodyMedium)
        }
    }
}