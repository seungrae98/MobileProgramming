package com.example.fridgefriend.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AllInbox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.AllInbox
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector

data class BarItem (val title :String, val selectIcon: ImageVector, val onSelectedIcon : ImageVector, val route:String)

object NavBarItems{
    val BarItems = listOf(
        BarItem(
            title = "Fridge",
            selectIcon = Icons.Default.AllInbox,
            onSelectedIcon = Icons.Outlined.AllInbox,
            route = "Fridge"
        ),
        BarItem(
            title = "Search",
            selectIcon = Icons.Default.Search,
            onSelectedIcon = Icons.Outlined.Search,
            route = "Search"
        ),
        BarItem(
            title = "Favourite",
            selectIcon = Icons.Default.Favorite,
            onSelectedIcon = Icons.Outlined.Favorite,
            route = "Favourite"
        ),
        BarItem(
            title = "Settings",
            selectIcon = Icons.Default.Settings,
            onSelectedIcon = Icons.Outlined.Settings,
            route = "Settings"
        )

    )
}