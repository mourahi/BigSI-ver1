package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.FavoriteBorder
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
                        title = "Phone-DP-SAFI",
                        label = "par nom ou num",
                        defaultText = "",
                        openEditor = phonesViewModel.openGroupsDialog
                    ) {
                        Log.d("adil", "valeur chercher = $it")
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
                        openedMenu.value = !openedMenu.value
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
            ItemMenu("هاتف",Icons.Default.Add, phonesViewModel.openPhoneDialog),
            ItemMenu("تدبير", Icons.Default.Check, phonesViewModel.openCardOperations),
        )
        val buttons = listOf(
            MyToggleI(selectFirst = true, icons = listOf(Icons.Filled.Favorite,Icons.Outlined.FavoriteBorder)){return@MyToggleI ""},
            MyToggleI(selectFirst = true, icons = listOf(Icons.Filled.CheckBox,Icons.Filled.CheckBoxOutlineBlank)){return@MyToggleI ""},
        )

        if (openedMenu.value) MoreMenu(openedMenu, mapMenu) //
        if (phonesViewModel.openGroupsDialog.value) EditGroupsDialog(
           title = "مجموعة الهاتف",
           groupsPhone = phonesViewModel.activeGroupsPhone.value,
            phonesViewModel.openGroupsDialog
        )
        if (phonesViewModel .openPhoneDialog.value) EditPhoneDialog(
           title = "Edit Phone",
           phone = Phone("primaire", tel = "066666", nom = "adil mourahi"),
           phonesViewModel
       )

        //Affichage
        Column(Modifier.fillMaxWidth()) {
            //travial sur CAT
            Log.d("adil","cats = ${phonesViewModel.cats.value}")
            CatFilter(phonesViewModel.cats.value,
                phonesViewModel.catsSelected.value, // Affichage
                ) {
                Log.d("adil","Fresultat =${it}")
            }
            CatFilter(phonesViewModel.subCats.value,
                phonesViewModel.subCatsSelected.value, // Affichage
            ) {
                Log.d("adil","FsubCatResultat =${it}")
            }
            // FIN CATFILTER

            if (phonesViewModel.openCardOperations.value) CardOperationsPhone(buttons)//todos:A complter
            PhonePageContent(
                phones = phonesViewModel.phones,
                isCardOperation = phonesViewModel.openCardOperations.value,
                isCloud = false,
                onInsert = {},
                onDelete = {phonesViewModel.delete(it) },
                onUpdate = {phonesViewModel.update(it)}
            )
        }
    }
}



/*
@Composable
private fun PhonesPageContent(phonesViewModel: PhonesViewModel) {
   LazyColumn{
       items(phonesViewModel.phones.value){
           CardPhone(ph = it)
       }
   }
}*/


