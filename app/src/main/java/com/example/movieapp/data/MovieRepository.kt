package com.example.movieapp.data

import com.example.movieapp.R
import com.example.movieapp.data.Movie

object MovieRepository {
    val movies = listOf(
        Movie(
            id = 1,
            title = "Inception",
            description = "A mind-bending thriller",
            posterResId = R.drawable.poster1, // ваш постер
            premiere = "2010",
            country = "USA",
            genre = "Sci-Fi",
            director = "Christopher Nolan",
            starring = "Leonardo DiCaprio, Joseph Gordon-Levitt",
            duration = "148 minutes"
        ),
        Movie(
            id = 2,
            title = "The Dark Knight",
            description = "A dark tale of a hero in Gotham",
            posterResId = R.drawable.poster2,
            premiere = "2008",
            country = "USA",
            genre = "Action",
            director = "Christopher Nolan",
            starring = "Christian Bale, Heath Ledger",
            duration = "152 minutes"
        ),
        // Добавьте остальные 8 фильмов с их параметрами
        Movie(
            id = 3,
            title = "Interstellar",
            description = "A journey beyond the stars",
            posterResId = R.drawable.poster3,
            premiere = "2014",
            country = "USA",
            genre = "Sci-Fi",
            director = "Christopher Nolan",
            starring = "Matthew McConaughey, Anne Hathaway",
            duration = "169 minutes"
        ),
        Movie(
            id = 4,
            title = "The Prestige",
            description = "Two magicians' rivalry",
            posterResId = R.drawable.poster4,
            premiere = "2006",
            country = "USA",
            genre = "Drama",
            director = "Christopher Nolan",
            starring = "Hugh Jackman, Christian Bale",
            duration = "130 minutes"
        ),

        Movie(
            id = 5,
            title = "Dunkirk",
            description = "WWII evacuation of Dunkirk",
            posterResId = R.drawable.poster5,
            premiere = "2017",
            country = "USA",
            genre = "War",
            director = "Christopher Nolan",
            starring = "Fionn Whitehead, Tom Hardy",
            duration = "106 minutes"
        ),
        Movie(
            id = 6,
            title = "Dunkirk",
            description = "WWII evacuation of Dunkirk",
            posterResId = R.drawable.poster6,
            premiere = "2017",
            country = "USA",
            genre = "War",
            director = "Christopher Nolan",
            starring = "Fionn Whitehead, Tom Hardy",
            duration = "106 minutes"
        ),
        Movie(
            id = 7,
            title = "Dunkirk",
            description = "WWII evacuation of Dunkirk",
            posterResId = R.drawable.poster7,
            premiere = "2017",
            country = "USA",
            genre = "War",
            director = "Christopher Nolan",
            starring = "Fionn Whitehead, Tom Hardy",
            duration = "106 minutes"
        ),
        Movie(
            id = 8,
            title = "Dunkirk",
            description = "WWII evacuation of Dunkirk",
            posterResId = R.drawable.poster8,
            premiere = "2017",
            country = "USA",
            genre = "War",
            director = "Christopher Nolan",
            starring = "Fionn Whitehead, Tom Hardy",
            duration = "106 minutes"
        ),
        Movie(
            id = 9,
            title = "Dunkirk",
            description = "WWII evacuation of Dunkirk",
            posterResId = R.drawable.poster9,
            premiere = "2017",
            country = "USA",
            genre = "War",
            director = "Christopher Nolan",
            starring = "Fionn Whitehead, Tom Hardy",
            duration = "106 minutes"
        ),
        Movie(
            id = 10,
            title = "Dunkirk",
            description = "WWII evacuation of Dunkirk",
            posterResId = R.drawable.poster10,
            premiere = "2017",
            country = "USA",
            genre = "War",
            director = "Christopher Nolan",
            starring = "Fionn Whitehead, Tom Hardy",
            duration = "106 minutes"
        ),

    )
}