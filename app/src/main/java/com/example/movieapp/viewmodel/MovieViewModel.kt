package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepository
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {

    private val movieRepository = MovieRepository()
    private val _selectedMovie = mutableStateOf<Movie?>(null)

    val selectedMovie: Movie? get() = _selectedMovie.value
    val movies = mutableStateOf<List<Movie>>(emptyList())

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    companion object{

    private val _currentMovieId = MutableLiveData<Int?>()
    val currentMovieId: LiveData<Int?> get() = _currentMovieId }

    init {
        loadMovies()
    }

    private fun loadMovies() {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null
            val result = movieRepository.fetchMovies()
            isLoading.value = false

            result.onSuccess {
                movies.value = it
            }.onFailure { error ->
                errorMessage.value = error.localizedMessage ?: "Unknown error"
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
        _currentMovieId.value = movie.id
    }




}