package com.example.movieapp.viewmodel

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.FavoriteMovieDao
import com.example.movieapp.data.FilterBadgeCache
import com.example.movieapp.data.FilterPreferences
import com.example.movieapp.data.FilterSettings
import com.example.movieapp.data.Movie
import com.example.movieapp.data.MovieRepository
import com.example.movieapp.data.NetworkUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


class MovieViewModel @Inject constructor(
    private val filterPreferences: FilterPreferences,
    private val favoriteMovieDao: FavoriteMovieDao,
    private val filterBadgeCache: FilterBadgeCache
) : ViewModel() {

    private val _favoriteMovies = MutableStateFlow<List<Movie>>(emptyList())
    val favoriteMovies: StateFlow<List<Movie>> = _favoriteMovies.asStateFlow()

    private val movieRepository = MovieRepository()
    private val _selectedMovie = mutableStateOf<Movie?>(null)

    private val _movies = MutableStateFlow<List<Movie>>(emptyList())
    val movies: StateFlow<List<Movie>> = _movies.asStateFlow()

    private val _filteredMovies = MutableStateFlow<List<Movie>>(emptyList())
    val filteredMovies: StateFlow<List<Movie>> = _filteredMovies.asStateFlow()

    private val _isFilterApplied = MutableStateFlow(false)
    val isFilterApplied: StateFlow<Boolean> = _isFilterApplied.asStateFlow()

    val isLoading = mutableStateOf(false)
    val errorMessage = mutableStateOf<String?>(null)

    private val _filterSettings = MutableStateFlow(FilterSettings("", "", false))
    val filterSettings: StateFlow<FilterSettings> = _filterSettings.asStateFlow()

    init {

        loadFilterSettings()
        viewModelScope.launch {
            favoriteMovieDao.getAllFavorites().collect { favorites ->
                _favoriteMovies.value = favorites
            }
        }
    }

    fun setFilterApplied(isApplied: Boolean) {
        _isFilterApplied.value = isApplied
    }

    fun isFilterApplied(): Boolean {
        return filterBadgeCache.isFilterApplied
    }

    fun toggleFavorite(movie: Movie) {
        viewModelScope.launch {
            if (_favoriteMovies.value.any { it.id == movie.id }) {
                favoriteMovieDao.removeFavorite(movie)
            } else {
                favoriteMovieDao.addFavorite(movie)
            }
        }
    }

    companion object {
        private val _currentMovieId = MutableLiveData<Int?>()
        val currentMovieId: LiveData<Int?> get() = _currentMovieId
    }

    fun loadMovies(context: Context) {
        viewModelScope.launch {
            isLoading.value = true
            errorMessage.value = null

            if (NetworkUtils.isNetworkAvailable(context)) {
                val result = movieRepository.fetchMovies()
                result.onSuccess {
                    _movies.value = it
                    applyFilters(
                        genre = _filterSettings.value.genre,
                        year = _filterSettings.value.year.toIntOrNull(),
                        russianOnly = _filterSettings.value.russianOnly
                    )
                }.onFailure { error ->
                    errorMessage.value = error.localizedMessage ?: "Unknown error"
                }
            } else {
                errorMessage.value = "Отсутствует подключение к сети"
            }

            isLoading.value = false
        }
    }

    private fun loadFilterSettings() {
        viewModelScope.launch {
            filterPreferences.filterSettings.collect { settings ->
                _filterSettings.value = settings
                applyFilters(
                    genre = settings.genre,
                    year = settings.year.toIntOrNull(),
                    russianOnly = settings.russianOnly
                )
            }
        }
    }

    fun selectMovie(movie: Movie) {
        _currentMovieId.value = movie.id
    }
    fun applyFilters(genre: String, year: Int?, russianOnly: Boolean) {
        _filteredMovies.value = _movies.value.filter { movie ->
            val matchesGenre = genre.isBlank() || movie.genre.contains(genre, ignoreCase = true)
            val matchesYear = year == null || movie.premiere == year
            val matchesRussian = !russianOnly || movie.country.contains("Россия", ignoreCase = true)
            matchesGenre && matchesYear && matchesRussian
        }
        saveFilterSettings(genre, year?.toString() ?: "", russianOnly)
        setFilterApplied(genre.isNotBlank() || year != null || russianOnly)
    }

    private fun saveFilterSettings(genre: String, year: String, russianOnly: Boolean) {
        viewModelScope.launch {
            filterPreferences.saveFilterSettings(FilterSettings(genre, year, russianOnly))
        }
    }

    fun resetFilters() {
        applyFilters("", null, false)
        setFilterApplied(false)
    }
}



