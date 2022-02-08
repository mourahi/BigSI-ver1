package com.mourahi.bigsi.groupsphone

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.R
import com.mourahi.bigsi.components.*
import com.mourahi.bigsi.groupsphone.editgroupsphone.GroupsPhoneViewModel
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain

@Composable
fun GroupsPhonePage(viewModelGPhone:GroupsPhoneViewModel= viewModel()){
    val openedMenu =  remember  { mutableStateOf(false)}

    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { MyTextField(
                    title = "Groups phone",
                    label = "search dp",
                defaultText = ""
                    ){
                    Log.d("adil","valeur chercher = $it")
                }
                        },
                navigationIcon = {
                    IconButton(onClick = { viewModelMain.navController.popBackStack()}) {
                    Icon(Icons.Default.Home, contentDescription = "return", tint = Color.White)
                }},
                actions = {
                    IconButton(onClick = {
                        openedMenu.value = !openedMenu.value
                    }) {
                        Icon(Icons.Default.MoreVert, contentDescription = "menu", tint = Color.White)
                    }
                },
            )
        }
    ) 
    {
        val mapMenu = listOf(
            ItemMenu("مجموعة",viewModelGPhone.openGroupsDialog,Icons.Default.Add),
            ItemMenu("تدبير",viewModelGPhone.openCardOperations,Icons.Default.Check),
        )
        val buttons = listOf(
            BtnOperation(Icons.Default.Star) { Log.d("adil", "je EMAIL") },
            BtnOperation(Icons.Default.Favorite) { Log.d("adil", "je favoris") },
            BtnOperation(checkOperation = {s->Log.d("adil","je check=$s")} ),
        )

        if(openedMenu.value)  MoreMenu(openedMenu,mapMenu)
        if(viewModelGPhone.openGroupsDialog.value) TitleDialog(
            title="مجموعة الهاتف",
            groupsPhone = null, //toujour new dans GroupPhonePage
            viewModelGPhone
        )

        Column(Modifier.fillMaxWidth()) {
            if(viewModelGPhone.openCardOperations.value) {
                CardOperations(buttons)
            }
            GroupsPhonePageContent(viewModelGPhone)
        }
    }
}

@Composable
private fun GroupsPhonePageContent(viewModelGPhone: GroupsPhoneViewModel) {
    LazyColumn{
        items(viewModelGPhone.gPhones.value){
            GroupsPhoneCard(viewModelGPhone,it)
        }
    }
}

@Composable
private fun GroupsPhoneCard(viewModelGPhone: GroupsPhoneViewModel,gPh: GroupsPhone){
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
                    //viewModelGPhone.initPhonesPage(gPh.link) //todo: open PHone
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
                IconButton(onClick = {  }) {
                    Icon(Icons.Default.Favorite, contentDescription = "favoris" )
                }
                if(viewModelGPhone.openCardOperations.value){
                    IconButton(onClick = { }) {
                        Icon(painterResource(id = R.drawable.ic_save), contentDescription = "Save")
                    }
                    MyCheckBox()
                }
            }

        }
    }
}