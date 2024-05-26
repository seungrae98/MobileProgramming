package com.example.fridgefriend.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fridgefriend.screen.FavouriteScreen
import com.example.fridgefriend.screen.FridgeScreen
import com.example.fridgefriend.screen.SearchScreen
import com.example.fridgefriend.screen.SettingsScreen
import com.example.fridgefriend.viewmodel.UserDataViewModel

fun NavGraphBuilder.mainNavGraph(navController: NavHostController,
                                 userDataViewModel: UserDataViewModel){

    navigation(startDestination = "Fridge", route="Home"){

        composable(route = Routes.Fridge.route) {
            FridgeScreen(navController, userDataViewModel)
        }

        composable(route = Routes.Search.route) {
            SearchScreen(navController, userDataViewModel)
        }

        composable(route = Routes.Favourite.route) {
            FavouriteScreen(navController, userDataViewModel)
        }

        composable(route = Routes.Settings.route) {
            SettingsScreen(navController, userDataViewModel)
        }
    }
}