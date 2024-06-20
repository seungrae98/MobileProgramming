package com.example.fridgefriend.screen

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController

@Composable
fun WebViewScreen(
    navController: NavHostController,
    navBackStackEntry: NavBackStackEntry
) {
    val url = navBackStackEntry.arguments?.getString("url") ?: "https://www.example.com"

    Column(Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.End
        ) {
            CloseButton {
                navController.popBackStack()
            }
        }
        WebViewComponent(url)
    }
}

@Composable
fun WebViewComponent(url: String) {
    Surface(modifier = Modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier.fillMaxSize(),
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadUrl(url)
                }
            }
        )
    }
}

@Composable
fun CloseButton(onClick: () -> Unit) {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "close tab",
        tint = Color.Black,
        modifier = Modifier
            .size(24.dp)
            .clickable(onClick = onClick)
    )
}
