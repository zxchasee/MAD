package com.example.mad_final.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.mad_final.R
import com.example.mad_final.models.Poster
import com.example.mad_final.service.apiService


@Composable
fun DetailedInfo(
    posterId: Int,
    navController: NavController
) {
    var poster by remember { mutableStateOf<Poster?>(null) }
    var isloading by remember { mutableStateOf<Boolean>(true) }

    LaunchedEffect(Unit) {
        try {
            poster = apiService.getPosterById(posterId).data
            isloading = false
        } catch (exception: Exception) {
            isloading = false
        }

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

        } else if (poster != null) {
            AsyncImage(
                modifier = Modifier.height(400.dp),
                model = "",
                placeholder = painterResource(id = R.drawable.thumbnail),
                error = painterResource(id = R.drawable.thumbnail),
                contentDescription = "Naushniki"
            )

            Text(
                text = poster!!.title,
                fontSize = 35.sp,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                fontWeight = FontWeight(500),
                color = Color.Black,
                textAlign = TextAlign.Center,
            )

            Text(
                "$" + poster!!.price.toString(),
                fontSize = 50.sp,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                fontWeight = FontWeight(500),
                color = Color.Black,
                textAlign = TextAlign.Center,
            )
            Text(
                poster!!.description.toString(),
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                color = Color.Black,
                textAlign = TextAlign.Left,
            )

            Text(
                poster?.url ?: "",
                fontSize = 20.sp,
                modifier = Modifier.fillMaxWidth().padding(10.dp),
                color = Color.Black,
                textAlign = TextAlign.Left,
            )
            Row(modifier = Modifier.fillMaxWidth().padding(10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                ExtendedFloatingActionButton(
                    modifier = Modifier,
                    containerColor = Color.Red,
                    onClick = {navController.navigate("/editPoster/"+poster!!.id)},
                    icon = { Icon(Icons.Filled.Edit, "Extended floating action button.") },
                    text = {
                        Text(
                            text = "Edit",
                            fontSize = 25.sp,
                            color = Color.White
                        )
                    },
                )

                ExtendedFloatingActionButton(
                    modifier = Modifier,
                    containerColor = Color.Red,
                    onClick = {},
                    icon = { },
                    text = {
                        Text(
                            text = "Call",
                            fontSize = 25.sp,
                            color = Color.White
                        )
                    },
                )
            }
        }


    }
}



