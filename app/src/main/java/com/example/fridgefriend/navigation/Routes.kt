package com.example.fridgefriend.navigation

sealed class Routes(val route: String) {
    object Login : Routes("Login")
    object Register : Routes("Register")
    object Home : Routes("Home")
    object Fridge : Routes("Fridge")
    object Search : Routes("Search")
    object Favourite : Routes("Favourite")
    object Settings : Routes("Settings")
}