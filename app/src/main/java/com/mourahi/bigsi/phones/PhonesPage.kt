package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.R
import com.mourahi.bigsi.components.*
import com.mourahi.bigsi.viewModelMain

@Composable
fun PhonesPage(phonesViewModel: PhonesViewModel= viewModel()) {
    val openedMenu = remember { mutableStateOf(false) }
    val openedFilter = rememberSaveable{ mutableStateOf(false) }
    val title = remember { mutableStateOf( phonesViewModel.activeGroupsPhone.name)}
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MyTextField(
                        title = title.value,
                        label = "الاسم او الهاتف",
                        defaultText = "",
                        openEditor = phonesViewModel.openGroupsDialog
                    ) {
                        phonesViewModel.search(it)
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModelMain.navController.popBackStack() }) {
                        Icon(
                            Icons.Default.ArrowForward,
                            contentDescription = "retrun",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { openedFilter.value = !openedFilter.value}) {
                        if(!openedFilter.value)  Icon( Icons.Filled.FilterAlt, contentDescription = "filter", tint = Color.White)
                        else Icon(painter = painterResource(id = R.drawable.filter_alt_off), contentDescription = null, tint = Color.White)
                    }

                    IconButton(onClick = { openedMenu.value = true })
                    {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "menu",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = { FloatingActionButton(onClick = { phonesViewModel.openPhoneDialog.value = true }) {
            Icon(Icons.Filled.AddBox, contentDescription = "add")
        }
        },
    ) {
        val mapMenu = listOf(
            ItemMenu("هاتف",Icons.Default.Add, phonesViewModel.openPhoneDialog){
                phonesViewModel.activePhone( Phone("",""))
            },
            ItemMenu("تدبير", Icons.Default.Edit, phonesViewModel.openCardOperations),
        )

        if (openedMenu.value) MoreMenu(openedMenu, mapMenu) //

        if (phonesViewModel.openGroupsDialog.value) EditGroupsDialog(
           title = "المجموعات",
           groupsPhone = phonesViewModel.activeGroupsPhone,
            phonesViewModel.openGroupsDialog
        ){
            if(it != null) {
                phonesViewModel.activeGroupsPhone = it
                title.value = it.name
                phonesViewModel.updateGroupsPhoneFromPhonePage(it)
            }
        }

        if (phonesViewModel.openPhoneDialog.value) EditPhoneDialog(
           title = "Edit Phone",
           phonesViewModel
       )

        //Affichage
        Column(Modifier.fillMaxWidth()) {
            //travial sur CAT
         if(openedFilter.value &&  phonesViewModel.cats.size>1) {
             CatFilter(
                 phonesViewModel.cats,
                 phonesViewModel.catsSelected, // Affichage
             ) {
                 Log.d("adil", "Fresultat =${it.toList()}")
                 phonesViewModel.filterByCats(it.toList())
             }
          if(phonesViewModel.subCats.size>0)   CatFilter(
                 phonesViewModel.subCats,
                 phonesViewModel.subCatsSelected, // Affichage
             ) {
                 Log.d("adil", "FsubCatResultat =${it}")
                 phonesViewModel.filterBySubCats(it.toList())
             }
         }
            // FIN CATFILTER

            if (phonesViewModel.openCardOperations.value) CardOperationsPhone(
               phonesViewModel.phones,
                onCheckAll = {
                    phonesViewModel.checkAll(it)
                },
                onUpdateList = { phonesViewModel.updateList(it) },
                onDeleteList = {phonesViewModel.deleteList(it) }
            )//todos:A complter

            PhonePageContent(
                phones = phonesViewModel.phones,
                onCardOperations = phonesViewModel.openCardOperations,
                onEdit = {
                    phonesViewModel.activePhone(it)
                    phonesViewModel.openPhoneDialog.value = true
                },
                onUpdate = {
                    phonesViewModel.update(it)
                },
                onSelect = {
                    phonesViewModel.activePhone(it)
                }
            )
        }
    }
}


