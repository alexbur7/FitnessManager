package ru.alexbur.fintess_manager.navigation

interface Navigator {
    fun navigateTo(route: Route, clearRoute: Route? = null)
    fun popBackStack()
}