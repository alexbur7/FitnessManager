package ru.alexbur.fintess_manager.common_presentation.snackbar

import androidx.compose.runtime.staticCompositionLocalOf
import ru.alexbur.fintess_manager.common_presentation.mvi.ShowSnackBar

val LocalSnackbar = staticCompositionLocalOf<(ShowSnackBar.Settings) -> Unit> {
    error("No LocalSnackbar provided")
}