package com.example.fridgefriend.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.fridgefriend.screen.FavouriteScreen
import com.example.fridgefriend.screen.FridgeScreen
import com.example.fridgefriend.screen.SearchScreen
import com.example.fridgefriend.screen.SettingsScreen
import com.example.fridgefriend.viewmodel.CardDataViewModel
import com.example.fridgefriend.viewmodel.IngredientDataViewModel
import com.example.fridgefriend.viewmodel.UserDataViewModel

fun NavGraphBuilder.mainNavGraph(navController: NavHostController,
                                 userDataViewModel: UserDataViewModel,
                                 cardDataViewModel: CardDataViewModel,
                                 ingredientDataViewModel: IngredientDataViewModel){
    navigation(startDestination = "Fridge", route="Main"){

        composable(route = Routes.Fridge.route) {
            FridgeScreen(navController, userDataViewModel, cardDataViewModel, ingredientDataViewModel)
        }

        composable(route = Routes.Search.route) {
            SearchScreen(navController, userDataViewModel, cardDataViewModel, ingredientDataViewModel)
        }

        composable(route = Routes.Favourite.route) {
            FavouriteScreen(navController, cardDataViewModel, userDataViewModel)
        }

        composable(route = Routes.Settings.route) {
            SettingsScreen(navController)
        }
    }
}