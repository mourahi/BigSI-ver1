package com.mourahi.bigsi.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.ui.theme.myPadding


@Composable
fun PhonePageContent(
    phones: MutableState<List<Phone>>,
    isCardOperation:Boolean,
    isCloud:Boolean,
    onInsert:(ph:Phone)->Unit,
    onDelete: (ph: Phone) -> Unit,
    onUpdate: (ph: Phone) -> Unit={},

    ) {
    LazyColumn{
        items(phones.value){
            CardPhone(ph =it, isCardOperation, isCloud,onInsert,onDelete, onUpdate)
        }
    }
}

@Composable
 fun CardPhone(ph: Phone,
                     isCardOperation:Boolean,
                     isCloud: Boolean,
                     onInsert:(ph: Phone)->Unit={},
                     onDelete:(ph:Phone)->Unit ={},
                     onUpdate:(ph:Phone)->Unit = {}
                     )
 {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = myPadding, start = myPadding, end = myPadding),
        elevation = myPadding
    ) {
        Row(
            Modifier
                .fillMaxSize()
                .padding(myPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = ph.ecole )
                Text(text = ph.nom)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {


                    MyToggleIcon(
                        selectFirst = false, // todo:a completer
                        icons = listOf(Icons.Filled.Edit) ) {
                        //todo: edit personnel Phones (open page Phone for edit)
                        return@MyToggleIcon ""
                    }



              MyToggleIcon(
                  selectFirst = ph.fav,
                    icons = listOf(Icons.Filled.Favorite,Icons.Outlined.FavoriteBorder) ) {
                 ph.fav = !ph.fav
                  onUpdate(ph)
                  return@MyToggleIcon ""
                }

               MyToggleIcon(
                  selectFirst = true, // todo:a completer
                    icons = listOf(Icons.Filled.CheckBox,Icons.Outlined.CheckBoxOutlineBlank) ) {
                   return@MyToggleIcon ""
                }
            }

        }
    }
}