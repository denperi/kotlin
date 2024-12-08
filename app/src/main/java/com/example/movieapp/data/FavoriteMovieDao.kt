package com.example.movieapp.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.movieapp.viewmodel.MovieViewModel
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM movies")
    fun getAllFavorites(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavorite(movie: Movie)

    @Delete
    suspend fun removeFavorite(movie: Movie)
}
class MovieViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val filterPreferences = FilterPreferences(context)
        val database = AppDatabase.getDatabase(context)
        val favoriteMovieDao = database.favoriteMovieDao()
        val filterBadgeCache = FilterBadgeCache()

        @Suppress("UNCHECKED_CAST")
        return MovieViewModel(filterPreferences, favoriteMovieDao, filterBadgeCache) as T
    }
}