package com.example.mad_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mad_final.R
import com.example.mad_final.models.Poster
import com.example.mad_final.service.apiService
import com.example.mad_final.utils.RandomPic

@Composable
fun HomeScreen(navController: NavController) {
    var posters by remember { mutableStateOf<List<Poster>?>(null) }
    var isloading by remember { mutableStateOf<Boolean>(true) }


    LaunchedEffect(Unit) {
        try {
            posters= apiService.getAll().data
            isloading = false
        } catch (exception: Exception) {
            isloading = false
        }

    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .padding(14.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isloading) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text("Loading...")
            }

        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center
            )
            {
                items(posters!!.size) {
                    Column(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Box() {
                            AsyncImage(
                                modifier = Modifier.size(300.dp, 250.dp),
                                model = "",
                                placeholder = painterResource(id = RandomPic.getRandomImage()),
                                error = painterResource(id = RandomPic.getRandomImage()),
                                contentDescription = "Naushniki",
                            )
                        }
                        Text(

                            posters!!.get(it).title,
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                            fontSize = 25.sp,

                            )

                        Text(
                            "$"+ posters!!.get(it).price.toString(),
                            modifier = Modifier.fillMaxSize(),
                            textAlign = TextAlign.Center,
                            fontSize = 30.sp,

                            )
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center,
                        ) {
                            Button(colors = ButtonDefaults.buttonColors(containerColor = Color.Gray),
                                onClick = {
                                navController.navigate(
                                    "/poster/"+posters!!.get(it).id,
                                )
                            }) { Text("MORE INFO")


                            }
                        }
                    }
                }
            }
        }
    }
}