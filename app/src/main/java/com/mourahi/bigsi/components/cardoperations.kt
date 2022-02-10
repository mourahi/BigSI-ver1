package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun CardOperations(buttons: List<BtnOperation>) {
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
                if (it.icon == null) {// c'est sur on veux ajouter MyCHeck

                    MyCheckBox(it.defaultChecked, it.checkOperation)
                } else {
                    IconButton(onClick = it.operation) {
                        Icon(it.icon, contentDescription = "", tint = Color.Black)
                    }
                }

            }

        }
    }
}

data class BtnOperation(
    val icon: ImageVector? = null,
    val operation: () -> Unit = {},
    val defaultChecked: Boolean = false,
    val checkOperation: (Boolean) -> Unit = {}
)