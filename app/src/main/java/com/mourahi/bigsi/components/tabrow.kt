package com.mourahi.bigsi.components

import androidx.compose.material.Icon
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun MyTabRow(tabData:List<Pair<String,ImageVector>>, tabIndex:MutableState<Int>) {
    TabRow(selectedTabIndex = tabIndex.value) {
        tabData.forEachIndexed { index, pair ->
            Tab(selected = tabIndex.value == index, onClick = {
                tabIndex.value = index
            }, text = {
                Text(text = pair.first)
            }, icon = {
                Icon(imageVector = pair.second, contentDescription = null)
            })
        }
    }
}