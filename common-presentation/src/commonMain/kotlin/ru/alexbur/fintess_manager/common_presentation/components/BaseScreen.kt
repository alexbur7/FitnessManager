package ru.alexbur.fintess_manager.common_presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.alexbur.fintess_manager.common_presentation.components.text.getText
import ru.alexbur.fintess_manager.common_presentation.components.toolbar.ToolbarState

@Composable
fun BaseScreen(
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    toolbarState: ToolbarState? = null,
    onBackClick: () -> Unit = {},
    content: @Composable ColumnScope.() -> Unit = {},
) {
    Box(modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (toolbarState != null) {
                TopAppBar(
                    title = { Text(toolbarState.title.getText()) },
                    navigationIcon = if (toolbarState.hasExit) {
                        {
                            IconButton(onClick = onBackClick) {
                                Text(text = "←")
                            }
                        }
                    } else {
                        null
                    },
                )
            }
            content()
        }
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
    }
}