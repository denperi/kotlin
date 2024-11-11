package com.example.movieapp.data

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MovieRepository {

    private val api: MovieApi

    init {

        val apiKey = "RV8XW27-E0RMD47-MQ81R0X-JGTP91J"

        val requestInterceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val requestWithHeaders = originalRequest.newBuilder()
                .addHeader("X-API-KEY", apiKey)
                .addHeader("accept", "application/json")
                .build()
            chain.proceed(requestWithHeaders)
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.kinopoisk.dev/v1.4/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()


        api = retrofit.create(MovieApi::class.java)
    }

    suspend fun fetchMovies(): Result<List<Movie>> {
        return try {
            val response = api.getMovies()
            val movies = response.docs.map { it.toMovie() }
            Result.success(movies)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}