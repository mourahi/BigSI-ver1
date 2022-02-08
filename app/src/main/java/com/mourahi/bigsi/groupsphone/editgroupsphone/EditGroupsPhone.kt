package com.mourahi.bigsi.groupsphone.editgroupsphone

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.mourahi.bigsi.R
import com.mourahi.bigsi.components.PhoneCard
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain

@Composable
fun EditGroupsPhonePage(){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Edit") },
                navigationIcon = { IconButton(onClick = { viewModelMain.navController.popBackStack()}) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "retrun", tint = Color.White)
                }},
                actions = {}
            )
        }
    ) {
    EditGroupsPhoneContent()
    }
}

@Composable
private fun EditGroupsPhoneContent() {
    Column{
            EditNameCard()
            AddPhones()
    }
}

@Composable
private fun AddPhones(){
    LazyColumn(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally){
        item {
            Button(onClick = { /*TODO*/ }) {
                Text("Ajouter phone")
            }
        }
        items(3){
            PhoneCard(Phone(ecole = "teste ecole", nom = "teste nom"))
        }
    }
}



@Composable
private fun EditNameCard(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(myPadding),
        elevation = myPadding
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(myPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            NameTxtField()

            IconButton(onClick = {  }) {
                Icon(Icons.Default.Refresh, contentDescription = "refresh" )
            }
            IconButton(onClick = {  }) {
                Icon(Icons.Default.Delete, contentDescription = "delete" )
            }
        }
    }
}

@Composable
private fun NameTxtField(){
        var txtFind  by  remember  { mutableStateOf("") }
        var opened  by  remember  { mutableStateOf(false) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {opened = !opened}) {
                if(opened) Icon( painterResource(id = R.drawable.ic_save),contentDescription = "save") else Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            OutlinedTextField(value = txtFind,
                onValueChange = {txtFind=it},
                leadingIcon = { IconButton(onClick = { txtFind="" }) {
                    Icon( Icons.Default.Clear, contentDescription = "clear", tint = Color.Black)
                }},
                maxLines = 1,
                label={ Text(text = "Find")},
                modifier = Modifier
                    .background(Color.White)
                    .width(if (!opened) 0.dp else TextFieldDefaults.MinWidth))

            if(!opened)  Text("Groups phone")

        }
    }
