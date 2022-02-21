package com.mourahi.bigsi.components

import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MoreMenu(openedMenu: MutableState<Boolean>, mapMenu: List<ItemMenu>) {
    DropdownMenu(
        expanded = openedMenu.value,
        onDismissRequest = { openedMenu.value = false }
    ) {
        mapMenu.forEach {
            DropdownMenuItem(onClick = {
                openedMenu.value = false
                if (it.operation != null) (it.operation)()
                it.state.value = !it.state.value
            }) {
                Icon(
                    it.icon,
                    contentDescription = it.title,
                    tint = if (it.state.value) Color.Red else Color.Black
                )
                Text(it.title, color = if (it.state.value) Color.Red else Color.Black)
            }
        }

    }
}

data class ItemMenu(
    val title: String,
    val icon: ImageVector,
    val state: MutableState<Boolean> = mutableStateOf(false),
    val operation: (() -> Unit)? = null
)
