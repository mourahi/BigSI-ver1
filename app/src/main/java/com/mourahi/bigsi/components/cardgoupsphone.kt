package com.mourahi.bigsi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Save
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
                    viewModelMain.navController.navigate("phonespage/${gPh.link}")
                },
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            Column {
                Text(text = gPh.name + " isFav:${gPh.isFav}")
                Text(text = gPh.region)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
/*                    IconButton(onClick = {
                       // Save GroupsPhone to Room
                        if(!gPh.isSavedFromServer) {
                            gPh.isSavedFromServer = true
                            onInsert(gPh)
                        } else {
                            onDelete(gPh)
                        }
                         // pour faire mise à jour du UI seulement
                    }) {
                        val t = if( gPh.isSavedFromServer) Color.Red else Color.Black
                        Icon( if(gPh.isSavedFromServer) Icons.Default.Clear else Icons.Filled.Save, contentDescription = "SaveToLocal", tint = t)
                    }*/

/*                    val isFav = remember { mutableStateOf(gPh.isFav) }
                    IconToggleButton(checked =isFav.value, onCheckedChange = {
                        gPh.isSavedFromServer = it
                       isFav.value = it
                   }) {
                        val tint = animateColorAsState(
                            if (isFav.value) Color.Blue
                           else Color.LightGray
                        )
                      Icon(Icons.Filled.Favorite, contentDescription = "favoris",tint=tint.value)
                    }*/

               // if(viewModelGPhone.openCardOperations.value){
/*                if(isCardOperation){
                    IconButton(onClick = { }) {
                        Icon(Icons.Filled.Save, contentDescription = "Save")
                    }
                    MyCheckBox()
                }*/

                // teste du nouveau toggle ---------------------
                MyToggleIcon(
                    selectFirst = gPh.isSavedFromServer,
                    icons = listOf(Icons.Filled.Clear,Icons.Filled.Save) ){
                    gPh.isSavedFromServer = !gPh.isSavedFromServer
                    if(gPh.isSavedFromServer) {
                        onInsert(gPh)
                    } else {
                        onDelete(gPh)
                    }
                }

              if(!isCloud)  MyToggleIcon(
                  selectFirst = gPh.isFav,
                    icons = listOf(Icons.Filled.Favorite,Icons.Outlined.FavoriteBorder) ) {
                 gPh.isFav = !gPh.isFav
                  onUpdate(gPh)
                }

              if(isCardOperation)  MyToggleIcon(
                  selectFirst = gPh.isFav,
                    icons = listOf(Icons.Filled.CheckBox,Icons.Outlined.CheckBoxOutlineBlank) ) {

                }

                // fin teste nouveau toogle -------------------
            }

        }
    }
}