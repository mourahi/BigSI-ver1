package com.mourahi.bigsi

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mourahi.bigsi.ui.theme.myPadding

@Composable
fun MainPage(){
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
          item{ MainFavoris() }
    }
        }

@Composable
private fun MainFavoris(){
    Column{
        repeat(10){
            MainFavorisCard()
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
                Text(text = "GroupsPhone", fontSize = 20.sp, modifier = Modifier.align(Alignment.Center), color = Color.White)
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
                Text(text = "News", fontSize = 20.sp, modifier = Modifier.align(Alignment.Center), color = Color.White)
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