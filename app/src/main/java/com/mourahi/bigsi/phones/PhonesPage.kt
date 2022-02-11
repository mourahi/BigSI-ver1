package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.viewModelMain

@Composable
fun PhonesPage(idSheet:String, phonesViewModel: PhonesViewModel= viewModel()) {
    val openedMenu = remember { mutableStateOf(false) }
    phonesViewModel.idSheet.value = idSheet
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
            BtnOperation(Icons.Default.Send) { Log.d("adil", "je send") },
            BtnOperation(Icons.Default.Email) { Log.d("adil", "je EMAIL") },
            BtnOperation(Icons.Default.Delete) { Log.d("adil", "je Delete") },
            BtnOperation(checkOperation = { s -> Log.d("adil", "je check=$s") }),
        )

        if (openedMenu.value) MoreMenu(openedMenu, mapMenu) //
        if (phonesViewModel.openGroupsDialog.value) EditGroupsDialog(
           title = "مجموعة الهاتف",
           groupsPhone = GroupsPhone( name = "DP-SAFI", region = "Marrakech-Safi"),
            phonesViewModel.openGroupsDialog
        )
        if (phonesViewModel .openPhoneDialog.value) EditPhoneDialog(
           title = "Edit Phone",
           phone = Phone(1, "primaire", tel = "066666", nom = "adil mourahi"),
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

            if (phonesViewModel.openCardOperations.value) CardOperations(buttons)
            PhonesPageContent(phonesViewModel)
        }
    }
}

@Composable
private fun PhonesPageContent(phonesViewModel: PhonesViewModel) {
   LazyColumn{
       items(phonesViewModel.phones.value){
           CardPhone(ph = it)
       }
   }
}


