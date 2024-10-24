package com.example.movieapp

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Профиль", style = androidx.compose.material3.MaterialTheme.typography.titleLarge)
        // Здесь вы можете добавить элементы профиля в будущем
    }
}