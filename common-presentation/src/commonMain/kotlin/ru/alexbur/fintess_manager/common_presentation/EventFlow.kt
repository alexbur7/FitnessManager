package ru.alexbur.fintess_manager.common_presentation

import kotlinx.coroutines.flow.Flow
import ru.alexbur.fintess_manager.common_presentation.mvi.ViewEvent

abstract class EventFlow : Flow<ViewEvent>