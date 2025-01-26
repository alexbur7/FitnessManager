package ru.alexbur.fintess_manager.presentation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun App() {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxWidth()) {
            val navController: NavHostController = rememberNavController()
            NavHost(
                navController = navController,
                startDestination = "login"
            ) {
                AppComposeScreenFactory(this).create(navController)
            }
        }
    }
}