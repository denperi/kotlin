package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.data.Movie
import com.example.movieapp.viewmodel.MovieViewModel

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
            painter = rememberAsyncImagePainter(model = movie.posterUrl),
            contentDescription = movie.title,
            modifier = Modifier.size(64.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = movie.title, style = MaterialTheme.typography.titleLarge)
            Text(text = movie.genre, style = MaterialTheme.typography.bodySmall)
        }
    }
}
@Composable
fun MovieListScreen(
    navController: NavController,
    movieViewModel: MovieViewModel = viewModel()
) {
    val movies = movieViewModel.movies.value
    val isLoading = movieViewModel.isLoading.value
    val errorMessage = movieViewModel.errorMessage.value

    when {
        isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        errorMessage != null -> {
            Text(text = "Ошибка: $errorMessage", modifier = Modifier.fillMaxSize())
        }
        else -> {
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
    }
}