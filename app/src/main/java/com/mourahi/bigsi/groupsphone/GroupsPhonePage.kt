package com.mourahi.bigsi.groupsphone

import android.util.Log
import androidx.compose.animation.animateColorAsState
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.R
import com.mourahi.bigsi.components.*
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain

@Composable
fun GroupsPhonePage(viewModelGPhone: GroupsPhoneViewModel = viewModel()){
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
        // More menu ------------------------------------------
        if(openedMenu.value) {
            val mapMenu = listOf(
                ItemMenu("مجموعة",Icons.Default.Add,viewModelGPhone.openGroupsDialog),
                ItemMenu("تدبير",Icons.Default.Check,viewModelGPhone.openCardOperations),
                ItemMenu(" الكل", Icons.Default.Delete,mutableStateOf(false),
                ) { viewModelGPhone.deleteAll() },
            )
            MoreMenu(openedMenu, mapMenu)
        }
        // EditGroupsDialog------------------------------------------
        if(viewModelGPhone.openGroupsDialog.value) EditGroupsDialog(
            title="مجموعة الهاتف",
            groupsPhone = null, //toujour new dans GroupPhonePage
            viewModelGPhone.openGroupsDialog,
        ){
                if(it != null) viewModelGPhone.insertGroupsPhone(it) else Log.d("adil","valeur null")
        }

        // CardOperations------------------------------------------
        Column(Modifier.fillMaxWidth()) {
            //tabRow

            val tabData = listOf(
               arrayOf("Local",Icons.Filled.Home,viewModelGPhone.gPhones.value),
                arrayOf("Distant", Icons.Filled.ShoppingCart,viewModelGPhone.gPhoneDistant.value)
            )
            val tabIndex = rememberSaveable { mutableStateOf(0) }
            MyTabRow(tabData,tabIndex)

            if(viewModelGPhone.openCardOperations.value) {
                val buttons = listOf(
                    BtnOperation(Icons.Filled.Star) { Log.d("adil", "je EMAIL") },
                    BtnOperation(Icons.Filled.Favorite) { Log.d("adil", "je favoris") },
                    BtnOperation(Icons.Filled.Save ) { Log.d("adil", "je sauvegarde") },
                    BtnOperation(checkOperation = {s->Log.d("adil","je check=$s")} ),
                )
                CardOperations(buttons)
            }
            // CircularProgressIndicator------------------------------------------
/*            val isCircular = remember { mutableStateOf( viewModelGPhone.gPhones.value.isEmpty() && viewModelGPhone.gPhoneDistant.value.isEmpty())}
           if(isCircular.value ) {
               CircularProgressIndicator(Modifier.align(Alignment.CenterHorizontally))
               val composableScope = rememberCoroutineScope()
              composableScope.launch {
                   delay(3000)
                  isCircular.value = false
               }
           }*/

            // Content------------------------------------------
         if(tabIndex.value == 0)  GroupsPhonePageContent(viewModelGPhone) else  GroupsPhonePageContent(viewModelGPhone,tabIndex.value)
        }
    }
}

@Composable
private fun GroupsPhonePageContent(viewModelGPhone: GroupsPhoneViewModel,tabIndex:Int=0) {
    LazyColumn{
        items(if(tabIndex == 0) viewModelGPhone.gPhones.value else viewModelGPhone.gPhoneDistant.value){
            GroupsPhoneCard(viewModelGPhone,it,tabIndex)
        }
    }
}

@Composable
private fun GroupsPhoneCard(viewModelGPhone: GroupsPhoneViewModel, gPh: GroupsPhone,tabIndex: Int){
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
             if(tabIndex > 0) {
                 IconButton(onClick = { // Save GroupsPhone to Room
                     //gPh.isSaved = true ==> saved from distant , and not personnel
                     gPh.isSaved = true
                     viewModelGPhone.insertGroupsPhone(gPh)
                 }) {
                     Icon(Icons.Default.Save, contentDescription = "SaveToLocal")
                 }
             }else{
                 val favored = remember { mutableStateOf(gPh.isFav)}
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