package com.mourahi.bigsi.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain


@Composable
fun GroupsPhonePageContent(
    gPhones: MutableState<List<GroupsPhone>>,
    isCardOperation:Boolean,
    isCloud:Boolean,
    onInsert:(gPh:GroupsPhone)->Unit,
    onDelete: (gPh: GroupsPhone) -> Unit,
    onUpdate: (gPh: GroupsPhone) -> Unit={},

) {
    LazyColumn{
        items(gPhones.value){
            CardGroupsPhone(gPh =it, isCardOperation, isCloud,onInsert,onDelete, onUpdate)
        }
    }
}

@Composable
 fun CardGroupsPhone(gPh: GroupsPhone,
                     isCardOperation:Boolean,
                     isCloud: Boolean,
                     onInsert:(gPh: GroupsPhone)->Unit={},
                     onDelete:(gPh:GroupsPhone)->Unit ={},
                     onUpdate:(gPh:GroupsPhone)->Unit = {}
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
                .padding(myPadding)
                .clickable {
                    viewModelMain.navController
                        .navigate("phonespage/${gPh.id.toString() +"*mourahi*"+ gPh.link}")
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = gPh.name + " isCheck:${gPh.isChecked}")
                Text(text = gPh.region)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {

                if(!isCloud && !gPh.isSavedFromServer){
                    MyToggleIcon(
                        selectFirst = false, // todo:a completer
                        icons = listOf(Icons.Filled.Edit) ) {
                        //todo: edit personnel phones (open page phone for edit)
                        return@MyToggleIcon ""
                    }
                }else {

                    MyToggleIcon(
                        selectFirst = gPh.isSavedFromServer,
                        icons = listOf(Icons.Filled.Clear,Icons.Filled.Save),
                        ){
                        Log.d("adil","(gPh.isSavedFromServer=${gPh.isSavedFromServer} && viewModelMain.isCo()=${viewModelMain.isCo()}")
                       if(!gPh.isSavedFromServer && !viewModelMain.isCo()) return@MyToggleIcon "Probleme de connexion"
                        gPh.isSavedFromServer = !gPh.isSavedFromServer
                        if(gPh.isSavedFromServer) { // todo:save gPh + ph
                           onInsert(gPh)
                        } else {
                            onDelete(gPh)
                        }
                        return@MyToggleIcon ""
                    }
                }


              if(!isCloud)  MyToggleIcon(
                  selectFirst = gPh.isFav,
                    icons = listOf(Icons.Filled.Favorite,Icons.Outlined.FavoriteBorder) ) {
                 gPh.isFav = !gPh.isFav
                  onUpdate(gPh)
                  return@MyToggleIcon ""
                }

              if(isCardOperation)  MyToggleIcon(
                  selectFirst = gPh.isChecked, // todo:a completer
                    icons = listOf(Icons.Filled.CheckBox,Icons.Outlined.CheckBoxOutlineBlank) ) {
                  return@MyToggleIcon ""
                }
            }

        }
    }
}