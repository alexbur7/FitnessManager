package ru.alexbur.fintess_manager.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import fitnessmanager.composeapp.generated.resources.Res
import fitnessmanager.composeapp.generated.resources.compose_multiplatform
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.viewmodel.koinViewModel

@Composable
@Preview
fun App() {
    MaterialTheme {
        Scaffold(modifier = Modifier.fillMaxWidth()) {
            val viewModel = koinViewModel<AppViewModel>()
            val viewState by viewModel.viewState.collectAsStateWithLifecycle()
            var showContent by remember { mutableStateOf(false) }
            Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }
                when (val state = viewState) {
                    AppViewState.Initial -> Unit
                    is AppViewState.ShowRockets -> {
                        LazyColumn {
                            items(state.data, { rocket -> rocket.id }) { rocket ->
                                Row(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = rocket.id.toString()
                                    )
                                    Text(
                                        text = rocket.country
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}