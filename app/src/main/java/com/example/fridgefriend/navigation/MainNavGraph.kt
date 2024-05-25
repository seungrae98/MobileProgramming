package com.example.fridgefriend.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fridgefriend.screen.FavouriteScreen
import com.example.fridgefriend.screen.FridgeScreen
import com.example.fridgefriend.screen.SearchScreen
import com.example.fridgefriend.screen.SettingsScreen

fun NavGraphBuilder.mainNavGraph(navController: NavHostController){
    navigation(startDestination = "Fridge", route="Main"){

        composable(route = Routes.Fridge.route) {
            FridgeScreen(navController)
        }

        composable(route = Routes.Search.route) {
            SearchScreen(navController)
        }

        composable(route = Routes.Favourite.route) {
            FavouriteScreen(navController)
        }

        composable(route = Routes.Settings.route) {
            SettingsScreen(navController)
        }
    }
}