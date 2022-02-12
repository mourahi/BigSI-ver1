package com.mourahi.bigsi.components

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mourahi.bigsi.viewModelMain

@Composable
fun  MyToggleIcon(selectFirst:Boolean=true,
                  icons: List<ImageVector>,
                  tint:Color = Color.Black,
                  onclick:(first:Boolean)->String){  // String = message Erreur pour Toast
    val sFirst = remember { mutableStateOf(true) }
    val state = remember { mutableStateOf("") }
    sFirst.value = selectFirst
    IconButton(onClick = {
       state.value =  onclick(!sFirst.value)
     if(state.value.isEmpty())  sFirst.value = !sFirst.value

    }) {
            if(state.value.isEmpty()) {
                if (sFirst.value || icons.size == 1) Icon(
                    icons[0],
                    contentDescription = null,
                    tint = tint
                )
                else Icon(icons[1], contentDescription = null, tint = tint)
            }else {
                if(sFirst.value ) Icon(icons[0], contentDescription = null, tint = tint)
                else Icon(icons[1], contentDescription = null, tint = tint)
                viewModelMain.toast(state.value)
            }
    }
}

data class MyToggleI(val selectFirst: Boolean, val icons: List<ImageVector>,val onclick: (first: Boolean) -> Unit)