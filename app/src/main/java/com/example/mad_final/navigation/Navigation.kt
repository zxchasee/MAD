package com.example.mad_final.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.example.mad_final.screens.AddScreen
import com.example.mad_final.screens.DetailedInfo
import com.example.mad_final.screens.EditScreen
import com.example.mad_final.screens.HomeScreen

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "/"


    ) {
        composable("/") {
            HomeScreen(navController)
        }
        composable("/add") {
            AddScreen(navController)
        }
        composable("/poster/{posterId}") { backStackEntry ->
            val posterId = backStackEntry.arguments?.getString("posterId")
            DetailedInfo(navController = navController, posterId = posterId!!.toInt())
        }


        composable("/editPoster/{posterId}") { backStackEntry ->
            val posterId = backStackEntry.arguments?.getString("posterId")
            EditScreen(navController = navController, posterId = posterId!!.toInt())
        }



    }
}