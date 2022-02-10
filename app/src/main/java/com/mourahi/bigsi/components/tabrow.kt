package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MyTabRow(tabData:List<Array<Any>>, tabIndex:MutableState<Int>) {
    TabRow(selectedTabIndex = tabIndex.value) {
        tabData.forEachIndexed { index, arr ->
            Tab(selected = tabIndex.value == index, onClick = {
                tabIndex.value = index
            }, text = {
                Row(verticalAlignment = Alignment.CenterVertically){
                    Text(text = arr[0].toString() + " (" + (arr[2] as List<*>).size + ")")
                    Icon(modifier = Modifier.padding(start = 4.dp), imageVector = arr[1] as ImageVector, contentDescription = null)
                }
            })
        }
    }
}