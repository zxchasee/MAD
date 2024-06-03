package com.example.mad_final.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.mad_final.models.Poster
import com.example.mad_final.service.apiService
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date

@Composable
fun EditScreen(
    posterId: Int,
    navController: NavController,
) {
    var poster by remember { mutableStateOf<Poster?>(null) }
    var isloading by remember { mutableStateOf<Boolean>(true) }
    val title = remember { mutableStateOf("") }
    val titleValid = remember { mutableStateOf(true) }
    val description = remember { mutableStateOf("") }
    val price = remember { mutableStateOf("") }
    val phone = remember { mutableStateOf("") }
    val url = remember { mutableStateOf("") }
    val color = remember { mutableStateOf("") }
    val viewModel = EditPosterViewModel()
    var showDialog by remember { mutableStateOf(false) } //error screen

    LaunchedEffect(Unit) {

        //In case if the user fails to fill the row, in stead of showing null,
        // we would be trying to catch the null and rename it to "nothing"

        try {
            poster = apiService.getPosterById(posterId).data
            title.value = poster!!.title
            description.value = poster!!.description ?: ""
            price.value = poster?.price ?: ""
            phone.value = poster!!.phone ?: ""
            url.value = poster!!.url ?: ""
            color.value = poster!!.color ?: ""


            isloading = false
        } catch (exception: Exception) {
            isloading = false
        }

    }

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
            .verticalScroll(rememberScrollState())
    ) {
        if (isloading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Loading...")
            }
        } else {
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

            TextField(
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

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = description.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                onValueChange = { description.value = it },
                singleLine = true,
                label = { Text("Description") }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = price.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = { price.value = it },
                singleLine = true,
                label = { Text("Price") }
            )

            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp),
                value = phone.value,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                onValueChange = { phone.value = it },
                singleLine = true,
                label = { Text("Phone") }
            )

            TextField(
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

            TextField(
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

            Row(modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Button(
                    onClick = {
                        viewModel.viewModelScope.launch {
                            val response = apiService.deletePoster(poster!!.id!!)

                            if (response.code==200) {
                                navController.navigate("/")
                            } else {
                                showDialog = true
                            }
                        }
                    },
                    modifier = Modifier
                        .height(75.dp)
                        .weight(0.5f)
                        .padding(vertical = 16.dp, horizontal = 5.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(196, 22, 22))
                ) {
                    Text(
                        text = "Delete"
                    )
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
                                    var response = apiService.updatePoster(posterId, poster)
                                    if (response.code == 200) {
                                        navController.navigate("/")
                                    } else {
                                        showDialog = true;
                                    }
                                } catch (e: Exception) {
                                    Log.d("zxc", e.message ?: "")

                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .height(75.dp)
                        .weight(0.5f)
                        .padding(vertical = 16.dp)
                ) {

                    Text(
                        text = "Save Changes"
                    )
                }

            }

        }

    }

}

class EditPosterViewModel : ViewModel() {}