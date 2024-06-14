package com.example.fridgefriend.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.fridgefriend.database.Repository
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.database.UserDataViewModelFactory
import com.example.fridgefriend.navigation.BottomNavigationBar
import com.example.fridgefriend.viewmodel.UserDataViewModel
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.navigation.mainNavGraph
import com.example.fridgefriend.viewmodel.UserData
import com.google.firebase.Firebase
import com.google.firebase.database.database

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController) {

    val context = LocalContext.current
    val table = Firebase.database.getReference("UserDB/users")
    val userDataDBViewModel: UserDataDBViewModel = viewModel(factory = UserDataViewModelFactory(Repository(table)))
    var selectedUser by remember {
        mutableStateOf<UserData?>(null)
    }
    val userList by userDataDBViewModel.userList.collectAsState()

    val navStoreOwner = rememberViewModelStoreOwner()

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {

        val userDataViewModel: UserDataViewModel =
            viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
        userDataViewModel.getUserData(userList)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Fridge Friend") }
                )
            },
            bottomBar = {
                if (userDataViewModel.loginStatus.value)
                    BottomNavigationBar(navController)
            }
        ) { contentPadding ->

            Column(modifier = Modifier.padding(contentPadding)) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Login.route
                ) {
                    composable(route = Routes.Login.route) {
                        LogInScreen(navController, userDataDBViewModel, userDataViewModel)
                    }

                    composable(route = Routes.Register.route) {
                        RegisterScreen(navController, userDataViewModel)
                    }

                    mainNavGraph(navController, userDataDBViewModel, userDataViewModel)

                }
            }
        }
    }
}