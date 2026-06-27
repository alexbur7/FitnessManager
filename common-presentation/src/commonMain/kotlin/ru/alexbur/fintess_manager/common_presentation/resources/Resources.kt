package ru.alexbur.fintess_manager.common_presentation.resources

import androidx.compose.runtime.Composable
import org.jetbrains.compose.resources.StringResource

@Composable
fun stringResource(resource: StringResource): String =
    org.jetbrains.compose.resources.stringResource(resource)

@Composable
fun stringResource(resource: StringResource, vararg formatArgs: Any): String =
    org.jetbrains.compose.resources.stringResource(resource, *formatArgs)
