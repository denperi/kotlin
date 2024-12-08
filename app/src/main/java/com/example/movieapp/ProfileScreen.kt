package com.example.movieapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.data.Profile
import com.example.movieapp.viewmodel.ProfileViewModel
import com.example.movieapp.viewmodel.openResume
import androidx.compose.runtime.collectAsState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController, viewModel: ProfileViewModel) {
    val profile by viewModel.profile.collectAsState(initial = Profile())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadProfile()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Профиль") },
                actions = {
                    IconButton(onClick = { navController.navigate("editProfile") }) {
                        Icon(Icons.Filled.Edit, contentDescription = "Редактировать профиль")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            profile.avatarUri.takeIf { it.isNotEmpty() }?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Аватар",
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(text = "ФИО: ${profile.fullName}")
            Text(text = "Должность: ${profile.position}")
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = { openResume(context, profile.resumeUrl) }) {
                Text("Резюме")
            }
        }
    }
}
