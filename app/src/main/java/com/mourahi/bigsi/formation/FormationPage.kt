package com.mourahi.bigsi.formation

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.viewModelMain

@Composable
fun FormationsPage(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "FormationsPage") },
                navigationIcon = { IconButton(onClick = { viewModelMain.navController.popBackStack()}) {
                    Icon(Icons.Default.Home, contentDescription = "retrun", tint = Color.White)
                }
                },
                actions = {}
            )
        }
    ) {
        FormationsPageContent()
    }
}

@Composable
private fun FormationsPageContent() {
    Text(" Forms page")
}