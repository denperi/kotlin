package com.example.movieapp.data

data class Movie(
    val id: Int,
    val title: String,
    val description: String,
    val posterResId: Int,  // Идентификатор ресурса для постера
    val premiere: String,
    val country: String,
    val genre: String,
    val director: String,
    val starring: String,
    val duration: String
)