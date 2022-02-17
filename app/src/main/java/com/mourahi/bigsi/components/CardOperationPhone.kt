package com.mourahi.bigsi.components

import android.util.Log
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
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun CardOperationsPhone(phones: List<Phone>,onCheckAll:(Boolean)->Unit,onUpdateList:(phones: List<Phone>)->Unit,onDeleteList:(phones: List<Phone>)->Unit) { //string pour 5/100 par exemple
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = myPadding, start = myPadding, end = myPadding),
        elevation = myPadding,
        backgroundColor = Color.LightGray
    )
    {
        Log.d("adil","CardOperatonPhone: CHANGEMENT ")
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = myPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            MyToggleIcon(icons =  listOf(Icons.Filled.Delete) ){
                val temp = mutableListOf<Phone>()
            phones.forEach {
                    if(it.isChecked) temp.add(it)
                }
              if(temp.size>0) onDeleteList(temp)
                return@MyToggleIcon if(temp.size<1) "Rien à supprimer" else ""
            }
            val ch = getChecked(phones)
            val fa = getFavored(phones)
            Row(verticalAlignment = Alignment.CenterVertically){
                Text(text = fa.second)
                MyToggleIcon(
                    selectFirst = fa.first,
                    icons = listOf(Icons.Filled.Favorite, Icons.Filled.FavoriteBorder)
                )
                { etatFav ->
                    val plusCheck = phones.filter { n -> n.isChecked }.size > 1
                    val plusFav = phones.any { n -> n.fav }
                    phones.forEach { g ->
                        g.fav = when(true){
                            ch.first  -> if(fa.first) !g.fav else true
                            fa.first -> if(g.isChecked) !g.fav else g.fav
                            plusCheck && !plusFav -> g.isChecked
                            plusCheck && plusFav -> if(g.isChecked) g.fav else g.isChecked
                            else ->  if(g.isChecked) !g.fav else g.fav
                        }
                    }
                    onUpdateList(phones)
                    return@MyToggleIcon ""
                }
            }


            Row(verticalAlignment = Alignment.CenterVertically){
                Text(text = ch.second)
                MyToggleIcon(
                    selectFirst =!ch.first,
                    icons =  listOf(Icons.Filled.CheckBoxOutlineBlank, Icons.Filled.CheckBox) )
                {
                        checked->
                       // checkAll(!checked, phones)
                    //viewModel.updateList(phones)
                    onCheckAll(!checked)

                    return@MyToggleIcon ""
                }
            }
        }
    }
}

private fun getChecked(ph:List<Phone>): Pair<Boolean,String> {
    val ch = ph.filter { it.isChecked }.size
    val t = ph.size
    return  Pair(ch==t,"$ch/$t")
}
private fun getFavored(ph:List<Phone>): Pair<Boolean,String> {
    val ch = ph.filter { it.fav }.size
    val t = ph.size
    return  Pair(ch==t,"$ch")
}
private fun checkAll(check:Boolean,phones:List<Phone>){
    phones.forEach {
        it.isChecked = check
    }
}
private fun favAll(check:Boolean,phones:List<Phone>){
    phones.forEach {
        it.fav = check
    }
}