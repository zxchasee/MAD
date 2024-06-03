package com.example.mad_final.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun BottomNavigationBar(navController: NavController) {
    BottomAppBar(

        actions = {
            IconButton(
                onClick = { navController.navigate("/") },
                modifier = Modifier.size(60.dp)
            ) {
                Icon(
                    Icons.Filled.Home,
                    contentDescription = ""
                )
            }
        },
        floatingActionButton = {
            FloatingActionButton(containerColor = Color(231,30,98),
                contentColor = Color.White,
                onClick = { navController.navigate("/add") }) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = "add",


                )

            }
        }
    )

}

