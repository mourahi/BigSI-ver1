package com.mourahi.bigsi.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain


@Composable
fun PhonePageContent(
    phones: List<Phone>,
    onCardOperations:MutableState<Boolean>,
    onEdit: (ph:Phone) -> Unit,
    onUpdate: (ph: Phone) -> Unit
    ) {
    val colla = remember{ mutableStateOf(-1) }
    LazyColumn{
            itemsIndexed(phones){index,item ->
                CardPhone(item,onCardOperations, onEdit, onUpdate,index,colla)
            }
    }
}

@Composable
 fun CardPhone(ph: Phone, oncardOperation: MutableState<Boolean>,
               onEdit:(ph:Phone)->Unit, onUpdate: (ph: Phone) -> Unit,index:Int,colla:MutableState<Int>)
 {
    Log.d("adil","CardPHone: phone=$ph")
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = myPadding, start = myPadding, end = myPadding),
        elevation = myPadding
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(myPadding))
        {
            Row(
                Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { viewModelMain.call(ph.tel) }) {
                    Icon(Icons.Filled.Phone, contentDescription = "PHone", tint = Color.Red)
                }

                Column(Modifier.clickable {
                    colla.value = if(colla.value == index) -1 else index
                }) {
                    Text(text = ph.ecole)
                    Text(text = ph.nom)
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    MyToggleIcon(
                        selectFirst = false, // todo:a completer
                        icons = listOf(Icons.Filled.Edit)
                    ) {
                        //todo: edit personnel phoneViewModel.phones.value (open page Phone for edit)
                        onEdit(ph)
                        return@MyToggleIcon ""
                    }
                    MyToggleIcon(
                        selectFirst = ph.fav,
                        icons = listOf(Icons.Filled.Favorite, Icons.Outlined.FavoriteBorder)
                    ) {
                        ph.fav = !ph.fav
                        onUpdate(ph)
                        return@MyToggleIcon ""
                    }

                    if (oncardOperation.value) MyToggleIcon(
                        selectFirst = !ph.isChecked, // todo:a completer
                        icons = listOf(Icons.Outlined.CheckBoxOutlineBlank, Icons.Filled.CheckBox)
                    )
                    {
                        ph.isChecked = !ph.isChecked
                        onUpdate(ph)
                        return@MyToggleIcon ""
                    }
                }
            }
            if( colla.value == index) Column(Modifier.fillMaxWidth()){
                Text(text = ph.tel)
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                )
                {
                    IconButton(onClick = {
                        viewModelMain.sendSMS(ph.tel)
                    }) {
                        Icon(Icons.Filled.Send, contentDescription = "send", tint = Color.Black)
                    }
                    IconButton(onClick = {
                        Log.d("adil","je send MAIL")
                        viewModelMain.sendMail(ph.email)
                    }) {
                        Icon(Icons.Filled.Mail, contentDescription = "mail", tint = Color.Black)
                    }
                    IconButton(onClick = {  Log.d("adil","je MAP")  }) {
                        Icon(Icons.Filled.MapsHomeWork, contentDescription = "Map", tint = Color.Black
                        )
                    }
                    IconButton(onClick = {  Log.d("adil","j'OUVRE PLUS")
                        viewModelMain.navController.navigate("detailsphone")
                    }) {
                        Icon(Icons.Filled.PlusOne, contentDescription = "Plus", tint = Color.Black)
                    }
                }
            }
        }
    }
}

