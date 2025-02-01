package ru.alexbur.fintess_manager.navigation

interface Navigator {
    fun navigateTo(route: Route)
    fun popBackStack()
}