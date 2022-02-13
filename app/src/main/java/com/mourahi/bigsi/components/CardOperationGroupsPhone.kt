package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun CardOperationsGroupsPhone(gphs: List<GroupsPhone>, onCheckedAll:(checked:Boolean)->String ) { //string pour 5/100 par exemple
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
            MyToggleIcon(icons =  listOf(Icons.Filled.Clear) ){

                return@MyToggleIcon ""
            }
            MyToggleIcon(icons =  listOf(Icons.Filled.Favorite,Icons.Outlined.FavoriteBorder) ){

                return@MyToggleIcon ""
            }
            val re = getChecked(gphs)
            Row(verticalAlignment = Alignment.CenterVertically){
                MyToggleIcon(
                     selectFirst =!re.first,
                    icons =  listOf(Icons.Filled.CheckBoxOutlineBlank,Icons.Filled.CheckBox) ){
                        checked-> onCheckedAll(!checked)
                    return@MyToggleIcon ""
                }
                Text(text = re.second)
            }

        }
    }
}
private fun getChecked(gphs:List<GroupsPhone>): Pair<Boolean,String> {
    val ch = gphs.filter { it.isChecked }.size
    val t = gphs.size
   return  Pair(ch==t,"$ch/$t")
}

