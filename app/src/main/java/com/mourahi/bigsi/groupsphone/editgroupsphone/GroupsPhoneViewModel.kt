package com.mourahi.bigsi.groupsphone.editgroupsphone

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.groupsphone.GroupsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class GroupsPhoneViewModel : ViewModel() {
    val openGroupsDialog = mutableStateOf(false)
    val openCardOperations = mutableStateOf(false)

    val gPhones = GroupsRepository.allData


    init {
        Log.d("adil", "GroupsPhoneViewModel: Initialisation groupsPhoneRepo")
        viewModelScope.launch {
            GroupsRepository.getAll(false)
            delay(100)
           if(gPhones.value.isNullOrEmpty()) GroupsRepository.getAll(true)
        }
    }



    fun insertGroupsPhone(gPh: GroupsPhone){
        viewModelScope.launch{ GroupsRepository.insertGPhone(gPh) }
    }

    fun deleteAll(){
        viewModelScope.launch {
            GroupsRepository.deleteAll()
        }
    }

}
