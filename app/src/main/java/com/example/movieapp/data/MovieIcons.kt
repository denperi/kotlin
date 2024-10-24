package com.example.movieapp.data

import androidx.annotation.DrawableRes
import com.example.movieapp.R

object MovieIcons {
    // Список иконок фильмов
    @DrawableRes
    val movieIconList = listOf(
        R.drawable.poster1,
        R.drawable.poster2,
        R.drawable.poster3,
        R.drawable.poster4,
        R.drawable.poster5,
        R.drawable.poster6,
        R.drawable.poster7,
        R.drawable.poster8,
        R.drawable.poster9,
        R.drawable.poster10
    )

    // Функция для получения иконки по индексу фильма
    fun getIconForMovie(index: Int): Int {
        return movieIconList.getOrNull(index) ?: R.drawable.ic_launcher_foreground // если нет иконки, возвращаем стандартную
    }
}