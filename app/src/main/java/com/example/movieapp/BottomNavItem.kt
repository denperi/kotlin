package com.example.movieapp

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home

import androidx.compose.material.icons.filled.Person
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem(var title: String, var icon: ImageVector, var route: String) {
    object Home : BottomNavItem("Home", Icons.Filled.Home, "home")
    object Movies : BottomNavItem("Movies", Icons.Filled.Home, "movieList")
    object Profile : BottomNavItem("Profile", Icons.Filled.Person, "profile")
}