package com.example.movieapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey val id: Int,
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

data class Profile(
    val fullName: String = "",
    val avatarUri: String = "",
    val resumeUrl: String = "",
    val position: String = "",
    val favoriteClassTime: String = ""
)
