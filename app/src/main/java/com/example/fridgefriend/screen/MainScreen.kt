package com.example.fridgefriend.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.fridgefriend.database.Repository
import com.example.fridgefriend.database.UserDataDBViewModel
import com.example.fridgefriend.database.UserDataViewModelFactory
import com.example.fridgefriend.navigation.BottomNavigationBar
import com.example.fridgefriend.viewmodel.UserDataViewModel
import com.example.fridgefriend.navigation.Routes
import com.example.fridgefriend.navigation.mainNavGraph
import com.example.fridgefriend.viewmodel.UserData
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.firebase.Firebase
import com.google.firebase.database.database
import com.google.firebase.database.FirebaseDatabase

@Composable
fun rememberViewModelStoreOwner(): ViewModelStoreOwner {
    val context = LocalContext.current
    return remember(context) { context as ViewModelStoreOwner }
}

val LocalNavGraphViewModelStoreOwner =
    staticCompositionLocalOf<ViewModelStoreOwner> {
        error("Undefined")
    }

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPermissionsApi::class)
@Composable
fun MainScreen(navController: NavHostController) {

    val context = LocalContext.current
    val table = FirebaseDatabase.getInstance().getReference("UserDB/users")
    val userDataDBViewModel: UserDataDBViewModel = viewModel(factory = UserDataViewModelFactory(Repository(table)))
    var selectedUser by remember {
        mutableStateOf<UserData?>(null)
    }
    val userList by userDataDBViewModel.userList.collectAsState()
    val navStoreOwner = rememberViewModelStoreOwner()

    val permission = rememberPermissionState(
        android.Manifest.permission.POST_NOTIFICATIONS
    )
    LaunchedEffect(key1 = Unit) {
        permission.launchPermissionRequest()
    }

    CompositionLocalProvider(
        LocalNavGraphViewModelStoreOwner provides navStoreOwner
    ) {

        val userDataViewModel: UserDataViewModel =
            viewModel(viewModelStoreOwner = LocalNavGraphViewModelStoreOwner.current)
        userDataViewModel.getUserData(userList)

        Scaffold(
            modifier = Modifier.background(Color(0xFFF68056)), // 전체 배경색 설정
            topBar = {
                if (userDataViewModel.loginStatus.value) {
                    TopAppBar(
                        title = { Text(text = "Fridge Friend", color = Color.White) },
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = Color(0xFFD95A43) // TopAppBar 배경색 설정
                        )
                    )
                }
            },
            bottomBar = {
                if (userDataViewModel.loginStatus.value)
                    BottomNavigationBar(navController)
            }
        ) { contentPadding ->

            Column(
                modifier = Modifier
                    .padding(contentPadding)
                    .background(Color(0xFFF68056)) // Column 배경색 설정
            ) {
                NavHost(
                    navController = navController,
                    startDestination = Routes.Login.route
                ) {
                    composable(route = Routes.Login.route) {
                        LogInScreen(navController, userDataDBViewModel, userDataViewModel)
                    }

                    composable(route = Routes.Register.route) {
                        RegisterScreen(navController, userDataDBViewModel, userDataViewModel)
                    }

                    mainNavGraph(navController, userDataDBViewModel, userDataViewModel)

                }
            }
        }
    }
}
