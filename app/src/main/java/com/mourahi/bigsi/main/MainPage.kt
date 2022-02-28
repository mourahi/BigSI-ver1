package com.mourahi.bigsi.main

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mourahi.bigsi.R
import com.mourahi.bigsi.components.CardGroupsPhone
import com.mourahi.bigsi.components.CardPhone
import com.mourahi.bigsi.components.MyTabRow
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain



@Composable
fun MainPage(){
    val tabIndex = rememberSaveable { mutableStateOf(0) }
    val colla = rememberSaveable { mutableStateOf(0) }
    val mu = remember { mutableStateOf(false) }
    LazyColumn(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.fillMaxSize()) {
            item {
                Text(text = "BigSI", fontSize = 30.sp)
            }
            item {
                MainBox()
            }
          item() {
              Text(
                  text = "Favoris", fontSize = 20.sp,
                  modifier= Modifier
                      .padding(myPadding)
                      .fillMaxWidth(), textAlign = TextAlign.Start
              )
          }
          item{
                Log.d("adil","reslutat = ${viewModelMain.allFavGroupsPhone}")
              val tabData = listOf(
                  arrayOf("المجموعة",Icons.Filled.Home, viewModelMain.allFavGroupsPhone),
                  arrayOf("الهواتف", Icons.Filled.ShoppingCart, viewModelMain.allFavPhones) )
              MyTabRow(tabData,tabIndex)
          }
        if(tabIndex.value == 0) {
            items(viewModelMain.allFavGroupsPhone) {
                CardGroupsPhone(
                    gPh = it,
                    isCardOperation = false,
                    isCloud = false,
                    onInsert = {},
                    onDelete = {},
                    onUpdate = { viewModelMain.updateGroupsPhone(it) }
                )
            }
        }else {
            itemsIndexed(viewModelMain.allFavPhones){ind,el ->
               CardPhone(
                   ph = el,
                   oncardOperation = mu,
                   onEdit ={} ,
                   onUpdate ={ viewModelMain.updatePhone(it)} ,
                   index =ind ,
                   colla = colla,
                   onSelect = {}
               )
            }
        }
        
    }
        }

@Composable
private fun MainFavorisCard(){
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
        Text(text = "un nom")
       IconButton(onClick = {  }) {
           Icon(Icons.Default.Favorite, contentDescription = "favoris" )
       }
    }
    }
}

@Composable
private fun MainBox() {
    val h = 140.dp
    Column(Modifier.fillMaxWidth()) {
        //GroupsPHone
        Row(
            Modifier
                .fillMaxWidth()
                .padding(myPadding)
        ) {
            Box(
                Modifier
                    .background(Color.Gray)
                    .height(h)
                    .weight(1f)
                    .clickable {
                        viewModelMain.navController.navigate("groupsphonepage")
                    }
            ){
               Image(painter = painterResource(id= R.drawable.bg) ,
                   contentDescription ="", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize() )
                Text(text = "التواصل", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center), color = Color.White)
                Icon(Icons.Filled.Phone, contentDescription = "", tint = Color.Yellow, modifier = Modifier.size(40.dp))
            }
            //News -------------------------------------------
            Box(
                Modifier
                    .background(Color.Green)
                    .height(h)
                    .weight(1f)
                    .clickable {
                        viewModelMain.navController.navigate("newspage")
                    }
            ){
                Image(painter = painterResource(id= R.drawable.news) ,
                    contentDescription ="", contentScale = ContentScale.Crop, modifier = Modifier.fillMaxSize() )
                Text(text = "المستجدات", fontSize = 30.sp, modifier = Modifier.align(Alignment.Center), color = Color.Red)
                Icon(Icons.Filled.Info, contentDescription = "", tint = Color.Yellow, modifier = Modifier.size(40.dp))
            }
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = myPadding)
                .border(1.dp, Color.Red)
        ) {
            //Formation -------------------------------------------
            Box(
                Modifier
                    .background(Color.Red)
                    .height(h)
                    .weight(1f)
                    .clickable {
                        viewModelMain.navController.navigate("formationspage")
                    }
            ){
                Text(text = "Formations", fontSize = 20.sp, modifier = Modifier.align(Alignment.Center), color = Color.White)
            }
            //FOrms -------------------------------------------
            Box(
                Modifier
                    .background(Color.Blue)
                    .height(h)
                    .weight(1f)
                    .clickable {
                        viewModelMain.navController.navigate("formspage")
                    }
            ){
                Text(text = "Forms", fontSize = 20.sp, modifier = Modifier.align(Alignment.Center), color = Color.White)
            }
        }
    }
}