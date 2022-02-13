package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun CardOperationsPhone(buttons: List<MyToggleI>) { //string pour 5/100 par exemple
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
            val nbr = remember { mutableStateOf("") }
            buttons.forEach {
                MyToggleIcon(icons = it.icons, selectFirst = it.selectFirst)
                { re-> nbr.value = it.onclick(re); return@MyToggleIcon ""
                }
            }
            Text("(${nbr.value})")
        }
    }
}

