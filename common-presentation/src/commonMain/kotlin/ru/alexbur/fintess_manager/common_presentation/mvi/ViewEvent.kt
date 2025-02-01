package ru.alexbur.fintess_manager.common_presentation.mvi

import ru.alexbur.fintess_manager.navigation.Route

abstract class ViewEvent

class Navigation(val route: Route) : ViewEvent()

class ShowSnackBar(val settings: Settings) : ViewEvent() {
    constructor(message: String) : this(Settings(message))

    data class Settings(
        val message: String,
    )
}

class PopBackStack(data: Any? = null) : ViewEvent()