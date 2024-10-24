package com.example.movieapp.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepository

class MovieViewModel : ViewModel() {

    // LiveData для списка фильмов
    private val _movies = MutableLiveData<List<Movie>>()
    val movies: LiveData<List<Movie>> = _movies

    // LiveData для выбранного фильма
    private val _selectedMovie = mutableStateOf<Movie?>(null)
    val selectedMovie: Movie? get() = _selectedMovie.value

    companion object {
        private val _currentMovieId = MutableLiveData<Int?>(null)
        val currentMovieId: LiveData<Int?> get() = _currentMovieId
    }

    init {
        // Инициализация списка фильмов из репозитория
        _movies.value = MovieRepository.movies
    }

    // Метод для установки выбранного фильма
    fun selectMovie(movie: Movie) {
        _selectedMovie.value = movie
        _currentMovieId.value = movie.id // Сохраняем ID выбранного фильма
    }

}