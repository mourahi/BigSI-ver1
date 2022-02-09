package com.mourahi.bigsi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.phones.PhonesViewModel
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain

@Composable
 fun PhoneCard(ph:Phone, phonesViewModel: PhonesViewModel= viewModel()){
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
            Row(verticalAlignment = Alignment.CenterVertically){
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Phone, "appel", tint = Color.Red)
                }
                Column(Modifier.clickable { viewModelMain.navController.navigate("detailsphone") }) {
                    Text(text = ph.ecole)
                    Text(text = ph.nom)
                }
            }
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.SpaceEvenly) {
                IconButton(onClick = {  }) {
                    Icon(Icons.Default.Favorite, contentDescription = "favoris" )
                }
                if(phonesViewModel.openCardOperations.value) {
                    IconButton(onClick = { phonesViewModel.openPhoneDialog.value = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "edit")
                    }

                    MyCheckBox()
                }
            }
        }
    }
}

@Composable
fun CardOperations(buttons:List<BtnOperation>) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = myPadding, start = myPadding, end = myPadding),
        elevation = myPadding,
        backgroundColor = Color.LightGray
    )
    {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(end = myPadding),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically)
        {
            buttons.forEach {
                if(it.icon == null){// c'est sur on veux ajouter MyCHeck

                    MyCheckBox(it.defaultChecked, it.checkOperation)
                }else{
                    IconButton(onClick =  it.operation ) {
                        Icon(it.icon, contentDescription = "", tint = Color.Black)
                    }
                }

            }

        }
    }
}
data class BtnOperation(val icon:ImageVector?=null ,val operation:()->Unit={},val defaultChecked: Boolean=false,val checkOperation:(Boolean)->Unit={})


@Composable
fun MoreMenu(openedMenu: MutableState<Boolean>, mapMenu: List<ItemMenu>){
    DropdownMenu(
        expanded = openedMenu.value,
        onDismissRequest = { openedMenu.value = false }
    ) {
        mapMenu.forEach {
            DropdownMenuItem(onClick = {
                openedMenu.value = false
              if(it.operation == null)  it.state.value= !it.state.value else (it.operation)()
            }) {
                Icon(it.icon, contentDescription =it.title,tint=if( it.state.value) Color.Red else Color.Black)
                Text(it.title, color = if( it.state.value) Color.Red else Color.Black)
            }
        }

    }
}
data class ItemMenu(
    val title:String,
    val state: MutableState<Boolean>,
    val icon:ImageVector,
    val operation: (() -> Unit)? = null
)


@Composable
fun MyCheckBox(defaultChecked:Boolean=false, checkOperation: (checked:Boolean) -> Unit={}) {
    val checkedState = remember { mutableStateOf(defaultChecked)}
    Checkbox(
        checked = checkedState.value,
        onCheckedChange = {
            checkedState.value = it
            checkOperation(it)
                          },
        modifier = Modifier.padding(myPadding)
    )
}