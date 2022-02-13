package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun CardOperationsGroupsPhone(gphs: List<GroupsPhone>,
                              onCheckedAll:(checked:Boolean)->String,
                              onUpdateList:(g:List<GroupsPhone>) -> Unit,
                              onDeleteList:(g:List<GroupsPhone>) -> Unit,
) { //string pour 5/100 par exemple
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
            MyToggleIcon(icons =  listOf(Icons.Filled.Delete) ){
                val temp = mutableListOf<GroupsPhone>()
                gphs.forEach {
                    if(it.isChecked) temp.add(it)
                }
                onDeleteList(temp)
                return@MyToggleIcon ""
            }
            val ch = getChecked(gphs)
            val fa = getFavored(gphs)
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(text = fa.second)
                MyToggleIcon(
                    selectFirst = fa.first,
                    icons = listOf(Icons.Filled.Favorite, Icons.Filled.FavoriteBorder)
                ) // todo:A complter ici pour click sur fav ---------------------------
                { etatFav ->
                    gphs.forEach { g ->
                       g.isFav = when(true){
                           ch.first  -> if(fa.first) !g.isFav else true
                           fa.first -> if(g.isChecked) !g.isFav else g.isFav
                           else -> if(g.isChecked) !g.isFav else g.isFav
                       }
                    }
                    onUpdateList(gphs)
                    return@MyToggleIcon ""
                }
            }


            Row(verticalAlignment = Alignment.CenterVertically){
                Text(text = ch.second)
                MyToggleIcon(
                     selectFirst =!ch.first,
                    icons =  listOf(Icons.Filled.CheckBoxOutlineBlank,Icons.Filled.CheckBox) )
                {
                        checked-> onCheckedAll(!checked)
                    return@MyToggleIcon ""
                }
            }

        }
    }
}
private fun getChecked(gphs:List<GroupsPhone>): Pair<Boolean,String> {
    val ch = gphs.filter { it.isChecked }.size
    val t = gphs.size
   return  Pair(ch==t,"$ch/$t")
}
private fun getFavored(gphs:List<GroupsPhone>): Pair<Boolean,String> {
    val ch = gphs.filter { it.isFav }.size
    val t = gphs.size
    return  Pair(ch==t,"$ch")
}

