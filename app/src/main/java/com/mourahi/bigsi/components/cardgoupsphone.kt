package com.mourahi.bigsi.components

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.mourahi.bigsi.R
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

) {
    LazyColumn{
        items(gPhones.value){
            CardGroupsPhone(gPh =it, isCardOperation, isCloud,onInsert,onDelete)
        }
    }
}

@Composable
 fun CardGroupsPhone(gPh: GroupsPhone,
                     isCardOperation:Boolean,
                     isCloud: Boolean,
                     onInsert:(gPh: GroupsPhone)->Unit={},
                     onDelete:(gPh:GroupsPhone)->Unit ={}
                     )
 {
     Log.d("adil","CardGroupsPHone:  isCloud=$isCloud ")
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
                Text(text = gPh.name)
                Text(text = gPh.region)
            }

            Row(verticalAlignment = Alignment.CenterVertically) {
                if(isCloud) {
                    val isSave = remember { mutableStateOf(gPh.isSaved)}
                    IconButton(onClick = { // Save GroupsPhone to Room
                        //gPh.isSaved = true ==> saved from distant , and not personnel
                        isSave.value = !isSave.value //todo: a completer pour supprimer
                        gPh.isSaved = isSave.value
                        if(isSave.value) onInsert(gPh) else onDelete(gPh)
                    }) {
                        val t = if(isSave.value) Color.Red else Color.Black
                        Icon(Icons.Default.Save, contentDescription = "SaveToLocal", tint = t)
                    }
                }else{
                    val favored = remember { mutableStateOf(gPh.isFav) }
                    IconToggleButton(checked =favored.value, onCheckedChange = {
                        Log.d("adil","click isFav = ${gPh.isFav} it=$it")
                        gPh.isSaved = it
                        favored.value = it
                    }) {
                        val tint = animateColorAsState(
                            if (favored.value) Color.Blue
                            else Color.LightGray
                        )
                        Icon(Icons.Filled.Favorite, contentDescription = "favoris",tint=tint.value)
                    }
                }
               // if(viewModelGPhone.openCardOperations.value){
                if(isCardOperation){
                    IconButton(onClick = { }) {
                        Icon(painterResource(id = R.drawable.ic_save), contentDescription = "Save")
                    }
                    MyCheckBox()
                }
            }

        }
    }
}