package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun MyCheckBox(defaultChecked: Boolean = false, checkOperation: (checked: Boolean) -> Unit = {}) {
    val checkedState = remember { mutableStateOf(defaultChecked) }
    Checkbox(
        checked = checkedState.value,
        onCheckedChange = {
            checkedState.value = it
            checkOperation(it)
        },
        modifier = Modifier.padding(myPadding)
    )
}