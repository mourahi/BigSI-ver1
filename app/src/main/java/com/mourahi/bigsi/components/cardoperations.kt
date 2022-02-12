package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun CardOperations(buttons: List<MyToggleI>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = myPadding, start = myPadding, end = myPadding),
        elevation = myPadding,
        backgroundColor = Color.LightGray
    )
    {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = myPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            buttons.forEach {
                MyToggleIcon(icons = it.icons, selectFirst = it.selectFirst){
                        re-> it.onclick(re);return@MyToggleIcon ""
                }
            }

        }
    }
}

