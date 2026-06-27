package ru.alexbur.fintess_manager.common_presentation.components.toolbar

import ru.alexbur.fintess_manager.common_presentation.components.text.ComposeTextStyle

data class ToolbarState(
    val title: ComposeTextStyle,
    val hasExit: Boolean
)
