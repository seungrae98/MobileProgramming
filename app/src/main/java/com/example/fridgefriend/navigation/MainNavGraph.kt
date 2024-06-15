package com.example.fridgefriend.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.screen.FavouriteScreen
import com.example.fridgefriend.screen.FridgeScreen
import com.example.fridgefriend.screen.SearchScreen
import com.example.fridgefriend.screen.SettingsScreen
import com.example.fridgefriend.screen.WebViewScreen
import com.example.fridgefriend.viewmodel.UserDataViewModel

fun NavGraphBuilder.mainNavGraph(navController: NavHostController,
                                 userDataDBViewModel: UserDataDBViewModel,
                                 userDataViewModel: UserDataViewModel){

    navigation(startDestination = "Fridge", route="Home"){

        composable(route = Routes.Fridge.route) {
            FridgeScreen(navController, userDataViewModel, userDataDBViewModel)
        }

        composable(route = Routes.Search.route) {
            SearchScreen(navController, userDataDBViewModel, userDataViewModel)
        }

        composable(route = Routes.Favourite.route) {
            FavouriteScreen(navController, userDataDBViewModel, userDataViewModel)
        }

        composable(route = Routes.Settings.route) {
            SettingsScreen(navController, userDataDBViewModel, userDataViewModel)
        }

        composable(
            route = Routes.WebView.route + "/{url}",
            arguments = listOf(navArgument("url") { type = NavType.StringType })
        ) { backStackEntry ->
            WebViewScreen(navController, backStackEntry)
        }
    }
}