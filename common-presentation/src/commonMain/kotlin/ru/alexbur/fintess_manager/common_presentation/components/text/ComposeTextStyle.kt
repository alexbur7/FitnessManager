package ru.alexbur.fintess_manager.common_presentation.components.text

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

@Immutable
sealed class ComposeTextStyle(val colorProvider: ColorComposeProvider) {
    data class Text(
        val data: String,
        val color: ColorComposeProvider = ColorComposeProvider { Color.Unspecified },
    ) : ComposeTextStyle(color)

    data class Resource(
        val resId: Int,
        val color: ColorComposeProvider = ColorComposeProvider { Color.Unspecified },
    ) : ComposeTextStyle(color)

    data class ResourceParams(
        val resId: Int,
        val params: List<Any>,
        val color: ColorComposeProvider = ColorComposeProvider { Color.Unspecified },
    ) : ComposeTextStyle(color)

    @Composable
    fun defaultColorIfUnspecified(color: Color) =
        if (this.colorProvider.getColor() == Color.Unspecified) {
            color
        } else {
            this.colorProvider.getColor()
        }
}

@Composable
fun ComposeTextStyle.getText(): String {
    return when (this) {
        is ComposeTextStyle.Resource -> {
            ""
        }

        is ComposeTextStyle.ResourceParams -> TODO()
        is ComposeTextStyle.Text -> TODO()
    }
}