package com.example.movieapp

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.movieapp.data.Profile
import com.example.movieapp.viewmodel.ProfileViewModel
import com.example.movieapp.viewmodel.saveBitmapToUri
import com.example.movieapp.viewmodel.saveUriToFile

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavController, viewModel: ProfileViewModel) {

    val context = LocalContext.current

    var fullName by remember { mutableStateOf(TextFieldValue("")) }
    var position by remember { mutableStateOf(TextFieldValue("")) }
    var resumeUrl by remember { mutableStateOf(TextFieldValue("")) }
    var avatarUri by remember { mutableStateOf<Uri?>(null) }
    var showAvatarDialog by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        fullName = TextFieldValue(viewModel.profile.value.fullName)
        position = TextFieldValue(viewModel.profile.value.position)
        resumeUrl = TextFieldValue(viewModel.profile.value.resumeUrl)
        avatarUri = viewModel.profile.value.avatarUri.takeIf { it.isNotEmpty() }?.let { Uri.parse(it) }
    }

    val pickImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                val savedUri = saveUriToFile(context, uri)
                if (savedUri != null) {
                    avatarUri = savedUri
                }
            }
        }
    )

    val takePictureLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { bitmap ->
            if (bitmap != null) {
                val uri = saveBitmapToUri(context, bitmap)
                if (uri != null) {
                    avatarUri = uri
                }
            }
        }
    )

    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                takePictureLauncher.launch()
            }
        }
    )

    val galleryPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted ->
            if (isGranted) {
                pickImageLauncher.launch("image/*")
            }
        }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад")
                    }
                },
                title = { Text("Редактирование профиля") }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            avatarUri?.let { uri ->
                Image(
                    painter = rememberAsyncImagePainter(uri),
                    contentDescription = "Avatar",
                    modifier = Modifier
                        .size(240.dp)
                        .clip(CircleShape)
                        .clickable {
                            showAvatarDialog = true
                        },
                    contentScale = ContentScale.Crop
                )
            } ?: Button(onClick = { showAvatarDialog = true }) {
                Text("Выбрать аватар")
            }

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = fullName,
                onValueChange = { fullName = it },
                label = { Text("ФИО") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = position,
                onValueChange = { position = it },
                label = { Text("Должность") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = resumeUrl,
                onValueChange = { resumeUrl = it },
                label = { Text("URL на резюме") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(onClick = {
                    viewModel.clearProfile()
                    navController.popBackStack()
                    viewModel.loadProfile()
                }) {
                    Text("Очистить профиль")
                }

                Button(onClick = {
                    viewModel.saveProfile(
                        Profile(
                            fullName = fullName.text,
                            avatarUri = avatarUri?.toString() ?: "",
                            resumeUrl = resumeUrl.text,
                            position = position.text
                        )
                    )

                    navController.popBackStack()
                    viewModel.loadProfile()
                }) {
                    Text("Готово")
                }
            }
        }
    }


    if (showAvatarDialog) {
        AlertDialog(
            onDismissRequest = { showAvatarDialog = false },
            title = { Text("Выбрать аватар") },
            text = {
                Column {
                    Button(onClick = {
                        galleryPermissionLauncher.launch(Manifest.permission.READ_MEDIA_IMAGES)
                        showAvatarDialog = false
                    }) {
                        Text("Выбрать из галереи")
                    }

                    Button(onClick = {
                        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        showAvatarDialog = false
                    }) {
                        Text("Сделать фото")
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showAvatarDialog = false }) {
                    Text("Отмена")
                }
            }
        )
    }
}