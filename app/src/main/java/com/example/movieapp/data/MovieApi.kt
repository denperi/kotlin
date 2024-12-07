package com.example.movieapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {
    @GET("movie")
    suspend fun getMovies(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 250,
        @Query("selectFields") selectFieldId: String = "id",
        @Query("selectFields") selectFieldName: String = "name",
        @Query("selectFields") selectFieldDescription: String = "description",
        @Query("selectFields") selectFieldYear: String = "year",
        @Query("selectFields") selectFieldCountries: String = "countries",
        @Query("selectFields") selectFieldGenres: String = "genres",
        @Query("selectFields") selectFieldMovieLength: String = "movieLength",
        @Query("selectFields") selectFieldPoster: String = "poster",
        @Query("selectFields") selectFieldPersons: String = "persons",
        @Query("selectFields") selectFieldTop250: String = "top250",
        @Query("notNullFields") notNullFieldName: String = "name",
        @Query("notNullFields") notNullFieldDescription: String = "description",
        @Query("notNullFields") notNullFieldYear: String = "year",
        @Query("notNullFields") notNullFieldCountriesName: String = "countries.name",
        @Query("notNullFields") notNullFieldGenresName: String = "genres.name",
        @Query("notNullFields") notNullFieldMovieLength: String = "movieLength",
        @Query("notNullFields") notNullFieldTop250: String = "top250",
        @Query("sortField") sortField: String = "top250",
        @Query("sortType") sortType: Int = 1,
        @Query("type") type: String = "movie"
    ): MovieResponse
}

object NetworkUtils {
    fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
    }
}