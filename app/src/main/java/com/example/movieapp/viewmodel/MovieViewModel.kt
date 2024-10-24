package com.example.movieapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepository

class MovieViewModel : ViewModel() {

    // LiveData для списка фильмов
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    // LiveData для выбранного фильма
    private val _selectedMovie = mutableStateOf<Movie?>(null)
    val selectedMovie get() = _selectedMovie

    init {
        // Инициализация списка фильмов из репозитория
        _movies.value = MovieRepository.movies
    }

    // Метод для установки выбранного фильма
    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
    }
}