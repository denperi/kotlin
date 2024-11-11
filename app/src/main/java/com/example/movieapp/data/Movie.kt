package com.example.movieapp.data

data class Movie(
    val id: Int,
    val title: String,
    val description: String?,
    val premiere: Int,
    val country: String,
    val genre: String,
    val duration: Int,
    val posterUrl: String,
    val director: String,
    val starring: String
)