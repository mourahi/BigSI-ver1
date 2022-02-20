package com.mourahi.bigsi.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun CatFilter(
    cats: List<String>,
    catsSelected: MutableList<String>,
    fResult: (catsSelected: MutableList<String>) -> Unit
) {
    Log.d("adil", "Start CatFilter cats.size = ${cats.size}")
    val deselectAll = remember { mutableStateOf(false) }
    val f = fun(cat: String) {
        if (catsSelected.contains(cat)) catsSelected.remove(cat)
        else catsSelected.add(cat)
        fResult(catsSelected)
    }
    Row(Modifier.fillMaxWidth())
    {
        IconButton(onClick = {
            fResult(mutableListOf())
            catsSelected.clear()
            deselectAll.value = true
        })
        {
            Icon(Icons.Default.Info, contentDescription = "deselect", tint = Color.Black)
        }
        LazyRow {
            items(cats) {
                CatItemFilter(it, f, catsSelected.contains(it), deselectAll)
            }
        }
    }
}

@Composable
fun CatItemFilter(
    cat: String,
    f: (cat: String) -> Unit,
    selected: Boolean,
    deselect: MutableState<Boolean>
) {
    val isSelected = remember { mutableStateOf(selected) }
    if (deselect.value) isSelected.value = false
    Box(
        Modifier
            .padding(start = myPadding, top = myPadding, bottom = myPadding)
            .border(1.dp, Color.Red)
            .background(if (isSelected.value) Color.Green else Color.White)
            .clickable {
                isSelected.value = !isSelected.value
                f(cat)
                deselect.value = false
            })
    { Text(cat, modifier = Modifier.padding(myPadding)) }
}
