package com.mourahi.bigsi.groupsphone

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mourahi.bigsi.components.isConnected
import kotlinx.coroutines.launch

class GroupsPhoneViewModel(application: Application) : AndroidViewModel(application) {
    val openGroupsDialog = mutableStateOf(false)
    val openCardOperations = mutableStateOf(false)

    val gPhones = GroupsPhoneRepository.allData
    val gPhoneDistant = GroupsPhoneRepository.allDataDistant

    init {
        Log.d("adil", "GroupsPhoneViewModel: Initialisation groupsPhoneRepo")
        gPhones.value = listOf()
        viewModelScope.launch {
            GroupsPhoneRepository.getAll(false)
            if(isConnected(application)) GroupsPhoneRepository.getAll(forServer = true)
        }
    }

    fun insertGroupsPhone(gPh: GroupsPhone){
        viewModelScope.launch{
            GroupsPhoneRepository.insertGPhone(gPh)
            gPhoneDistant.value -= gPh
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            GroupsPhoneRepository.deleteAll()
        }
    }

}