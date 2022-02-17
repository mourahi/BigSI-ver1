package com.mourahi.bigsi.components

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.phones.PhonesViewModel

@Composable
fun EditGroupsDialog(
    title: String,
    groupsPhone: GroupsPhone,
    openGroupsDialog: MutableState<Boolean>,
    onSave: (groupsPhone: GroupsPhone?) -> Unit = {}
) {
    val name = remember { mutableStateOf(groupsPhone.name) }
    val region = remember { mutableStateOf(groupsPhone.region) }

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = title) },
        text = {
            Column(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text(text = "name") }
                )
                OutlinedTextField(
                    value = region.value,
                    onValueChange = { region.value = it },
                    label = { Text(text = "region") }
                )
            }

        },
        confirmButton = { // if gPhone == null ===>  NEW
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = {
                    Log.d("adil", "save ")
                    openGroupsDialog.value = false  //on update et pas  on save

                   onSave(GroupsPhone(name = name.value, region = region.value))
                }) {
                    Text(text = "save")
                }
                Button(onClick = {
                    Log.d("adil", "cancel")
                    openGroupsDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
                Button(onClick = {
                    Log.d("adil", "delete ")
                    openGroupsDialog.value = false
                }) {
                    Text(text = "delete")
                }
            }
        },
        dismissButton = {},

        )
}

@Composable // dialog PHONE (NOM, TEL , CAT)
fun EditPhoneDialog(
    title: String,
    viewModel: PhonesViewModel,
) {
    val nom = remember { mutableStateOf(viewModel.activePhone.nom ) } //nom
    val tel = remember { mutableStateOf(viewModel.activePhone.tel ) } // tel
    val catOrEcole = remember { mutableStateOf(viewModel.activePhone.cycle ) } // ecole

    AlertDialog(
        onDismissRequest = { },
        title = { Text(text = title) },
        text = {
            Column(Modifier.fillMaxWidth()) {
                OutlinedTextField(
                    value = nom.value,
                    onValueChange = { nom.value = it },
                    label = { Text(text = "الاسم الكامل") }
                )
                OutlinedTextField(
                    value = tel.value,
                    onValueChange = { tel.value = it },
                    label = { Text(text = "الهاتف") }
                )
                OutlinedTextField(
                    value = catOrEcole.value,
                    onValueChange = { catOrEcole.value = it },
                    label = { Text(text = "الفئة") }
                )
            }
        },
        confirmButton = { // if phone == null ===>  NEW
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Button(onClick = { // todo:a completer with isNew
                    Log.d("adil", "save ")
                    viewModel.openPhoneDialog.value = false
                    if(viewModel.activePhone.ref == 0){
                        viewModel.insertPhone( Phone(nom=nom.value, tel = tel.value, ecole = catOrEcole.value, refgroup =viewModel.activeGroupsPhone.id  ))
                    }else {
                        val p = viewModel.activePhone
                        p.nom = nom.value
                        p.tel = tel.value
                        p.ecole=catOrEcole.value
                        viewModel.update(viewModel.activePhone)
                    }
                }) {
                    Text(text = "save")
                }
                Button(onClick = {
                    Log.d("adil", "cancel")
                    viewModel.openPhoneDialog.value = false
                }) {
                    Text(text = "Cancel")
                }
                Button(onClick = {
                    Log.d("adil", "delete ")
                    viewModel.openPhoneDialog.value = false
                    viewModel.delete(viewModel.activePhone)
                }) {
                    Text(text = "delete")
                }
            }
        },
        dismissButton = {}

    )
}