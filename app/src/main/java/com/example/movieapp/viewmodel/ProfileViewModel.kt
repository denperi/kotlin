package com.example.movieapp.viewmodel


import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.movieapp.data.Profile
import com.example.movieapp.data.ProfilePreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class ProfileViewModel(private val profilePreferences: ProfilePreferences) : ViewModel() {

    private val _profile = MutableStateFlow(Profile())
    val profile: StateFlow<Profile> = _profile.asStateFlow()

    init {
        loadProfile()
    }
    fun loadProfile() {
        viewModelScope.launch {
            val savedProfile = profilePreferences.getProfile()
            _profile.value = savedProfile
        }
    }

    fun saveProfile(updatedProfile: Profile) {
        viewModelScope.launch {
            profilePreferences.saveProfile(updatedProfile)
            loadProfile()
        }
    }
    fun clearProfile() {
        viewModelScope.launch {
            val emptyProfile = Profile()
            profilePreferences.saveProfile(emptyProfile)
            loadProfile()
        }
    }

}
fun openResume(context: Context, url: String) {
    if (url.isBlank()) {
        Toast.makeText(context, "Ссылка на резюме не заполнена", Toast.LENGTH_SHORT).show()
        return
    }

    val fixedUrl = if (url.startsWith("http://") || url.startsWith("https://")) {
        url
    } else {
        "http://$url"
    }

    val intent = Intent(Intent.ACTION_VIEW).apply {
        data = Uri.parse(fixedUrl)
        addCategory(Intent.CATEGORY_BROWSABLE)
    }

    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Toast.makeText(context, "Ошибка при открытии ссылки: ${e.localizedMessage}", Toast.LENGTH_LONG).show()
    }
}

fun saveBitmapToUri(context: Context, bitmap: Bitmap): Uri? {
    val fileName = "avatar_${System.currentTimeMillis()}.jpg"
    val directory = context.getExternalFilesDir("Pictures/MovieApp")

    if (directory != null && !directory.exists()) {
        directory.mkdirs()
    }

    val file = File(directory, fileName)

    return try {
        FileOutputStream(file).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
        }
        Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

fun saveUriToFile(context: Context, uri: Uri): Uri? {
    val fileName = "avatar_${System.currentTimeMillis()}.jpg"
    val directory = context.getExternalFilesDir("Pictures/MovieApp")

    if (directory != null && !directory.exists()) {
        directory.mkdirs()
    }

    val file = File(directory, fileName)

    return try {
        context.contentResolver.openInputStream(uri)?.use { inputStream ->
            FileOutputStream(file).use { outputStream ->
                inputStream.copyTo(outputStream)
            }
        }
        Uri.fromFile(file)
    } catch (e: IOException) {
        e.printStackTrace()
        null
    }
}

class ProfileViewModelFactory(private val profilePreferences: ProfilePreferences) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return ProfileViewModel(profilePreferences) as T
    }
}