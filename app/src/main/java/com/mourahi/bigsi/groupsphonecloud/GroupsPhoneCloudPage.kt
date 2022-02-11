package com.mourahi.bigsi.groupsphonecloud

import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mourahi.bigsi.components.GroupsPhonePageContent
import com.mourahi.bigsi.components.MyTextField
import com.mourahi.bigsi.viewModelMain

@Composable
fun GroupsPhoneCloudPage(groupsPhoneCloudViewModel: GroupsPhoneCloudViewModel= viewModel())
{
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { MyTextField(
                    title = "Cloud",
                    label = "search dp",
                    defaultText = ""
                ){
                    Log.d("adil","valeur chercher = $it")
                }
                },
                navigationIcon = {
                    IconButton(onClick = { viewModelMain.navController.popBackStack() }) {
                        Icon(Icons.Filled.ArrowForward, contentDescription = "return", tint = Color.White)
                    }
                },
                actions = {},
            )
        }
    )
    {
        GroupsPhonePageContent(
            gPhones = groupsPhoneCloudViewModel.gPhones,
            isCardOperation = false,
            isCloud = true,
            onInsert =  { gPh -> groupsPhoneCloudViewModel.insertGroupsPhone(gPh)},
            onDelete = {gPh ->  groupsPhoneCloudViewModel.delete(gPh)}
        )
            }

    }

