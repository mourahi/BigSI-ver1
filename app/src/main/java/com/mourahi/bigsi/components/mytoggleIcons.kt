package com.mourahi.bigsi.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun  MyToggleIcon(selectFirst:Boolean=true, icons: List<ImageVector>, onclick:(first:Boolean)->Unit){
    val sFirst = remember { mutableStateOf(true) }
    sFirst.value = selectFirst
    IconButton(onClick = {
        sFirst.value = !sFirst.value
        onclick(sFirst.value)
    }) {
        if(sFirst.value || icons.size == 1) Icon(icons[0], contentDescription = null, tint = Color.Black)
        else  Icon(icons[1], contentDescription = null, tint = Color.Black)
    }
}

data class MyToggleI(val selectFirst: Boolean, val icons: List<ImageVector>,val onclick: (first: Boolean) -> Unit)