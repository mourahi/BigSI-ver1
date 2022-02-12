package com.mourahi.bigsi.groupsphone

import android.app.Application
import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class GroupsPhoneViewModel(application: Application) : AndroidViewModel(application) {
    val openGroupsDialog = mutableStateOf(false)
    val openCardOperations = mutableStateOf(false)

    val gPhones = GroupsPhoneRepository.allData
   // val gPhoneDistant = GroupsPhoneRepository.allDataDistant

    init {
        Log.d("adil", "GroupsPhoneViewModel: Initialisation groupsPhoneRepo")
        gPhones.value = listOf()
        viewModelScope.launch {
            GroupsPhoneRepository.getAll(false)
        }
    }

    fun insertGroupsPhone(gPh: GroupsPhone) {

        viewModelScope.launch{
          GroupsPhoneRepository.insertGPhone(gPh)
        }

    }

    fun delete(gPh:GroupsPhone){
        viewModelScope.launch {
            GroupsPhoneRepository.delete(gPh)
        }
    }

    fun update(gPh: GroupsPhone){
        viewModelScope.launch {
            GroupsPhoneRepository.updateGphone(gPh)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            GroupsPhoneRepository.deleteAll()
        }
    }

}