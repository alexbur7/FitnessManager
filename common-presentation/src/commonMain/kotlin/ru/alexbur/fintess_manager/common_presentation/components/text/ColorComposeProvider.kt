package ru.alexbur.fintess_manager.common_presentation.components.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
fun interface ColorComposeProvider {

    @Composable
    fun getColor(): Color
}