package ru.alexbur.fintess_manager.feature.calendar.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel
import ru.alexbur.fintess_manager.navigation.Navigator

@Composable
internal fun CalendarScreen(
    navigator: Navigator,
    viewModel: CalendarViewModel = koinViewModel()
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Тут должен быть календарь")
        Spacer(Modifier.height(8.dp))
        Button(
            onClick = { navigator.popBackStack() }
        ) {
            Text("Вернуться назад")
        }
    }
}

