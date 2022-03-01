package com.mourahi.bigsi.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.SearchOff
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun MyTextField(
    title: String = "",
    label: String = "",
    defaultText: String = "",
    openEditor: MutableState<Boolean>? = null, onChange: (defaultText: String) -> Unit
) {
    var txtFind by remember { mutableStateOf(defaultText) }
    var opened by remember { mutableStateOf(false) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        IconButton(onClick = { opened = !opened }) {
            if (opened) Icon(
                Icons.Outlined.SearchOff,
                contentDescription = "stop search"
            ) else Icon(Icons.Default.Search, contentDescription = "open", tint = Color.White)
        }
        TextField(
            value = txtFind,
            textStyle = TextStyle(Color.Black),
            onValueChange = {
                txtFind = it
                onChange(it)
            },
            leadingIcon = {
                IconButton(onClick = { txtFind = "" }) {
                    Icon(Icons.Default.Clear, contentDescription = "clear", tint = Color.Black)
                }
            },
            maxLines = 1,
            label = { Text(text = label) },
            modifier = Modifier
                .background(Color.White)
                .width(if (!opened) 0.dp else TextFieldDefaults.MinWidth)
        )

        if (!opened) {
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable {
                if (openEditor != null) openEditor.value = true
            }) {
                Text(title)
                if (openEditor != null) Icon(
                    Icons.Default.Edit,
                    contentDescription = "EditTitle",
                    tint = Color.White
                )
            }
        }

    }
}




