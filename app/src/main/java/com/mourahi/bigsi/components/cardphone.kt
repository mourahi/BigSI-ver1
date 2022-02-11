package com.mourahi.bigsi.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.phones.PhonesViewModel
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain

// a revoir pour supprimer
@Composable
fun CardPhone(ph: Phone, phonesViewModel: PhonesViewModel = viewModel()) {
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
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Phone, "appel", tint = Color.Red)
                }
                Column(Modifier.clickable { viewModelMain.navController.navigate("detailsphone") }) {
                    Text(text = ph.ecole)
                    Text(text = ph.nom)
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                IconButton(onClick = { }) {
                    Icon(Icons.Default.Favorite, contentDescription = "favoris")
                }
                if (phonesViewModel.openCardOperations.value) {
                    IconButton(onClick = { phonesViewModel.openPhoneDialog.value = true }) {
                        Icon(Icons.Default.Edit, contentDescription = "edit")
                    }

                    MyCheckBox()
                }
            }
        }
    }
}