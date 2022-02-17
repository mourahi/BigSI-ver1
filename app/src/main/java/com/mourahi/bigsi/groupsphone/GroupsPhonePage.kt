package com.mourahi.bigsi.groupsphone

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.components.*
import com.mourahi.bigsi.viewModelMain

@Composable
fun GroupsPhonePage(viewModelGPhone: GroupsPhoneViewModel = viewModel()){
    val openedMenu =  remember  { mutableStateOf(false)}
    Scaffold(
      modifier = Modifier.fillMaxSize(),
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
                    MyToggleIcon(icons = listOf(Icons.Filled.CloudDownload) , tint = Color.White, onclick ={
                        if(!viewModelMain.isCo()) return@MyToggleIcon "Erreur Connexion"
                        viewModelMain.navController.navigate("groupscloudpage")
                        return@MyToggleIcon ""
                    } )

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
                ItemMenu("تدبير",Icons.Default.Check,viewModelGPhone.openCardOperations){
                                      viewModelGPhone.checkAll(false)
                },
                ItemMenu(" الكل", Icons.Default.Delete,mutableStateOf(false),
                ) { viewModelGPhone.deleteAll() },
            )
            MoreMenu(openedMenu, mapMenu)
        }
        // EditGroupsDialog------------------------------------------
        if(viewModelGPhone.openGroupsDialog.value) EditGroupsDialog(
            title="مجموعة الهاتف",
            groupsPhone = GroupsPhone("",""), //toujour new dans GroupPhonePage
            viewModelGPhone.openGroupsDialog,
        ){
                if(it != null) viewModelGPhone.insertGroupsPhone(it,true) else Log.d("adil","valeur null")
        }

        // CardOperations------------------------------------------
        Column(Modifier.fillMaxWidth()) {
            //tabRow
/*             val tabData = listOf(
              arrayOf("Local",Icons.Filled.Home,viewModelGPhone.gPhones.value),
               arrayOf("Distant", Icons.Filled.ShoppingCart,viewModelGPhone.gPhoneDistant.value) )
           val tabIndex = rememberSaveable { mutableStateOf(0) }
            MyTabRow(tabData,tabIndex)*/
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

            // CardOperation --------------------------------------------

            if(viewModelGPhone.openCardOperations.value) CardOperationsGroupsPhone(
                gphs =  viewModelGPhone.gPhones,
                onCheckedAll = { checked ->
                    viewModelGPhone.checkAll(checked)
                    return@CardOperationsGroupsPhone viewModelGPhone.getNbrChecked()
                },
                onUpdateList = {
                    viewModelGPhone.updateList(it)
                },
                onDeleteList = {
                    viewModelGPhone.deleteList(it)
                }
            )

            // Content------------------------------------------
                GroupsPhonePageContent(
                    gPhones = viewModelGPhone.gPhones,
                    isCardOperation = viewModelGPhone.openCardOperations.value,
                    isCloud = false,
                    onInsert = {},
                    onDelete = {viewModelGPhone.delete(it) },
                    onUpdate = {viewModelGPhone.update(it)}
                )
        }
    }
}

