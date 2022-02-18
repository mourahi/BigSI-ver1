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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.phones.PhonesRepository
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain


@Composable
fun GroupsPhonePageContent(
    gPhones: List<GroupsPhone>,
    isCardOperation:Boolean=false,
    isCloud:Boolean = false,
    onInsert:(gPh:GroupsPhone)->Unit={},
    onDelete: (gPh: GroupsPhone) -> Unit={},
    onUpdate: (gPh: GroupsPhone) -> Unit={},

) {
    LazyColumn{
        items(gPhones){
            CardGroupsPhone(it, isCardOperation, isCloud,onInsert,onDelete, onUpdate)
        }
    }
}

@Composable
 fun CardGroupsPhone(
    gPh: GroupsPhone,
    isCardOperation:Boolean,
    isCloud: Boolean,
    onInsert:(gPh: GroupsPhone)->Unit,
    onDelete:(gPh:GroupsPhone)->Unit ,
    onUpdate:(gPh:GroupsPhone)->Unit,
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
                .clickable { // open Phone : params string = "id+*mourahi*+idsheet"
                    PhonesRepository.activeGroupsPhone = gPh
                    viewModelMain.navController
                        .navigate("phonespage")
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = gPh.name)
                Text(text = gPh.region)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {

               /* if(!isCloud && !gPh.isSavedFromServer){
                    MyToggleIcon(
                        selectFirst = false, // todo:a completer
                        icons = listOf(Icons.Filled.Edit) ) {
                        //todo: edit personnel phones (open page phone for edit)
                        return@MyToggleIcon ""
                    }
                }*/

                if(!isCardOperation)    MyToggleIcon(
                    selectFirst = gPh.isSavedFromServer,
                    icons =if(isCloud) listOf(Icons.Filled.Clear,Icons.Filled.Save) else listOf(Icons.Filled.Delete),

                    )
                {
                    Log.d("adil","(gPh.isSavedFromServer=${gPh.isSavedFromServer} && viewModelMain.isCo()=${viewModelMain.isCo()}")
                    if(!gPh.isSavedFromServer && !viewModelMain.isCo()) return@MyToggleIcon "Probleme de connexion"
                    gPh.isSavedFromServer = !gPh.isSavedFromServer
                    if(gPh.isSavedFromServer) {
                        Log.d("adil","1/ cardGroupsPHnoe:gph id=${gPh.id} , link=${gPh.link}")
                       onInsert(gPh)
                    } else {
                        onDelete(gPh)
                    }
                    return@MyToggleIcon ""
                }



              if(!isCloud)  MyToggleIcon(
                  selectFirst = gPh.isFav,
                    icons = listOf(Icons.Filled.Favorite,Icons.Outlined.FavoriteBorder) ) {
                 gPh.isFav =it
                  onUpdate(gPh)
                  return@MyToggleIcon ""
                }

              if(isCardOperation)  MyToggleIcon(
                  selectFirst = gPh.isChecked, // todo:a completer
                    icons = listOf(Icons.Filled.CheckBox,Icons.Outlined.CheckBoxOutlineBlank) ) {
                  gPh.isChecked = it
                  onUpdate(gPh)
                  return@MyToggleIcon ""
                }
            }

        }
    }
}