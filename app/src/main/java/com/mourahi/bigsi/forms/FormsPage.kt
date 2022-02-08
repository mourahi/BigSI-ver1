package com.mourahi.bigsi.forms

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.viewModelMain

@Composable
fun FormsPage(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Forms") },
                navigationIcon = { IconButton(onClick = { viewModelMain.navController.popBackStack()}) {
                    Icon(Icons.Default.Home, contentDescription = "retrun", tint = Color.White)
                }},
                actions = {}
            )
        }
    ) {
        FormsPageContent()
    }
}

@Composable
private fun FormsPageContent() {
    Text(" Forms page")
}


