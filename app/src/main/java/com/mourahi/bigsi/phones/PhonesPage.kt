package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.components.*
import com.mourahi.bigsi.viewModelMain

@Composable
fun PhonesPage(phonesViewModel: PhonesViewModel= viewModel()) {
    val openedMenu = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    MyTextField(
                        title = phonesViewModel.activeGroupsPhone.name,
                        label = "par nom ou num",
                        defaultText = "",
                        openEditor = phonesViewModel.openGroupsDialog
                    ) {
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
                    IconButton(onClick = {
                        openedMenu.value = true
                    }) {
                        Icon(
                            Icons.Default.MoreVert,
                            contentDescription = "menu",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) {
        val mapMenu = listOf(
            ItemMenu("هاتف",Icons.Default.Add, phonesViewModel.openPhoneDialog){
            phonesViewModel.activePhone = Phone("","")
            },
            ItemMenu("تدبير", Icons.Default.Check, phonesViewModel.openCardOperations),
        )

        if (openedMenu.value) MoreMenu(openedMenu, mapMenu) //

        if (phonesViewModel.openGroupsDialog.value) EditGroupsDialog(
           title = "مجموعة الهاتف",
           groupsPhone = phonesViewModel.activeGroupsPhone,
            phonesViewModel.openGroupsDialog
        )

        if (phonesViewModel.openPhoneDialog.value) EditPhoneDialog(
           title = "Edit Phone",
           phonesViewModel
       )

        //Affichage
        Column(Modifier.fillMaxWidth()) {
            //travial sur CAT
            CatFilter(phonesViewModel.cats,
                phonesViewModel.catsSelected, // Affichage
                ) {
                Log.d("adil","Fresultat =${it}")
            }
            CatFilter(phonesViewModel.subCats,
                phonesViewModel.subCatsSelected, // Affichage
            ) {
                Log.d("adil","FsubCatResultat =${it}")
            }
            // FIN CATFILTER

            if (phonesViewModel.openCardOperations.value) CardOperationsPhone(
               phonesViewModel.phones,
                onCheckAll = {
                    phonesViewModel.checkAll(it)
                },
                onUpdateList = { phonesViewModel.updateList(it) }
            )//todos:A complter

            PhonePageContent(
                phones = phonesViewModel.phones,
                onCardOperations = phonesViewModel.openCardOperations,
                onEdit = {
                    phonesViewModel.activePhone = it
                    phonesViewModel.openPhoneDialog.value = true
                },
                onUpdate = {
                    phonesViewModel.activePhone = it
                    phonesViewModel.update(it)
                }
            )
        }
    }
}


