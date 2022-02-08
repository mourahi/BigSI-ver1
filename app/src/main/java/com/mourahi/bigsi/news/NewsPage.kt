package com.mourahi.bigsi.news

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.viewModelMain

@Composable
fun NewsPage(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NewsPage") },
                navigationIcon = { IconButton(onClick = { viewModelMain.navController.popBackStack()}) {
                    Icon(Icons.Default.Home, contentDescription = "retrun", tint = Color.White)
                }
                },
                actions = {}
            )
        }
    ) {
        NewsPageContent()
    }
}

@Composable
private fun NewsPageContent() {
    Text(" Forms page")
}