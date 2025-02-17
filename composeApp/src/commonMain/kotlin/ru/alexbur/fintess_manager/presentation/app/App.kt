package ru.alexbur.fintess_manager.presentation.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import ru.alexbur.fintess_manager.common_presentation.mvi.ShowSnackBar
import ru.alexbur.fintess_manager.common_presentation.snackbar.LocalSnackbar
import ru.alexbur.fintess_manager.presentation.AppComposeScreenFactory
import ru.alexbur.fintess_manager.presentation.rememberNavigator

@Composable
internal fun App(
    viewModel: AppViewModel = koinViewModel()
) {
    MaterialTheme {
        val snackbarHostState = remember { SnackbarHostState() }
        val scope = rememberCoroutineScope()
        val showSnackbar = { settings: ShowSnackBar.Settings ->
            snackbarHostState.currentSnackbarData?.dismiss()
            scope.launch {
                snackbarHostState.showSnackbar(settings.message)
            }
            Unit
        }
        CompositionLocalProvider(LocalSnackbar provides showSnackbar) {
            Scaffold(
                modifier = Modifier.fillMaxWidth(),
                snackbarHost = {
                    Box(modifier = Modifier.fillMaxSize()) {
                        SnackbarHost(snackbarHostState, Modifier.align(Alignment.TopCenter))
                    }
                }
            ) {
                val navController = rememberNavController()
                val navigator = rememberNavigator(navController)
                NavHost(
                    navController = navController,
                    startDestination = viewModel.startDestination
                ) {
                    AppComposeScreenFactory(this).create(navigator)
                }
            }
        }
    }
}