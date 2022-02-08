package com.mourahi.bigsi.groupsphone.editgroupsphone

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.repository.Repo
import kotlinx.coroutines.launch

class GroupsPhoneViewModel:ViewModel() {
    val openGroupsDialog = mutableStateOf(false)
    val openCardOperations = mutableStateOf(false)

    val gPhones = mutableStateOf(listOf<GroupsPhone>())


    init {
        Log.d("adil","GroupsPhoneViewModel: Initialisation groupsPhoneRepo")
        viewModelScope.launch {
            Repo.updateListGroupsPhone()
            gPhones.value = Repo.groupsPhoneRepo.value!!
        }
    }




}