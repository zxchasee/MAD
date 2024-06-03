package com.example.mad_final.screens

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import java.util.Calendar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.mad_final.models.Poster
import com.example.mad_final.service.apiService
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Date


@Composable
fun AddScreen(navController: NavController) {
    val viewModel = CreatePosterViewModel()
    var showDialog by remember { mutableStateOf(false) }
    val title = remember { mutableStateOf("") }
    val titleValid = remember { mutableStateOf(true) }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val url = remember { mutableStateOf("") }
    val color = remember { mutableStateOf("") }
    var imageUri by remember { mutableStateOf<Uri?>(null) }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.data?.let { uri ->
                    imageUri = uri
                }
            }
        }
    )



    if (showDialog) {
        AlertDialog(
            icon = {
                Icon(
                    Icons.Filled.Error,
                    contentDescription = "",
                    modifier = Modifier
                        .size(78.dp)
                        .padding(8.dp),
                )
            },
            title = { Text(text = "Error") },
            onDismissRequest = { showDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        showDialog = false
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .align(Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier.fillMaxWidth()
            )
            {
                Text(
                    text = "Create Post",
                    fontSize = 34.sp,
                    fontWeight = FontWeight(500),
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(7.dp, 7.dp, 7.dp, 25.dp)
                )
            }



            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = title.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    title.value = it
                    titleValid.value = title.value.length >= 3
                },
                singleLine = true,
                isError = !titleValid.value,
                label = { Text("Title") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = description.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { description.value = it },
                singleLine = true,
                label = { Text("Description") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = price.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { price.value = it },
                singleLine = true,
                label = { Text("Price") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = phone.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                onValueChange = { phone.value = it },
                singleLine = true,
                label = { Text("Phone") }
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = color.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    color.value = it
                },
                singleLine = true,
                label = { Text("Color") })

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = url.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = {
                    url.value = it
                },
                singleLine = true,
                label = { Text("URL") })

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(onClick = {
                    val intent = Intent(Intent.ACTION_PICK).apply {
                        type = "image/*"
                    }
                    launcher.launch(intent)
                }) {
                    Text("Upload Image")
                }
            }

            Button(
                onClick = {
                    viewModel.viewModelScope.launch {
                        if (titleValid.value) {
                            val poster = Poster(
                                title = title.value,
                                description = description.value,
                                phone = phone.value,
                                price = price.value,
                                color = color.value,
                                url = url.value,
                                date = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Date())
                            )
                            try {
                                var response = apiService.createPoster(poster)
                                if (response.code == 201) {
                                    navController.navigate("/")
                                } else {
                                    showDialog = true;
                                }
                            } catch (e: Exception) {
                                Log.d("zxc", e.message ?:"")

                            }
                        }
                    }

                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(231,30,98)),
                modifier = Modifier.fillMaxWidth().height(75.dp).padding(vertical = 16.dp)
            ) {
                Text(
                    text = "Create"
                )
            }
        }
    }
}


class CreatePosterViewModel : ViewModel() {}
