package com.example.movieapp.data

data class MovieResponse(
    val docs: List<MovieDoc>
)

data class MovieDoc(
    val id: Int,
    val name: String,
    val description: String,
    val year: Int,
    val countries: List<Country>,
    val genres: List<Genre>,
    val movieLength: Int,
    val poster: Poster?,
    val persons: List<Person>
)
data class Poster(val url: String)

data class Genre(val name: String)

data class Country(val name: String)

data class Person(
    val name: String,
    val profession: String
)

fun MovieDoc.toMovie(): Movie {

    val directors = persons.filter { it.profession == "режиссеры" }.map { it.name }
    val actors = persons.filter { it.profession == "актеры" }.map { it.name }.take(10)

    return Movie(
        id = id,
        title = name,
        description = description,
        premiere = year,
        country = countries.joinToString { it.name },
        genre = genres.joinToString { it.name },
        duration = movieLength,
        posterUrl = poster?.url ?: "",
        director = directors.joinToString(", "),
        starring = actors.joinToString(", ")
    )
}
