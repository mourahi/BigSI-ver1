package com.mourahi.bigsi.groupsphonecloud

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.mourahi.bigsi.components.isConnected
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.groupsphone.GroupsPhoneRepository
import kotlinx.coroutines.launch

class GroupsPhoneCloudViewModel(application: Application) : AndroidViewModel(application) {
    val gPhones =GroupsPhoneRepository.allDataDistant

    init {
        Log.d("adil", "GroupsPhoneCloudViewModel: Initialisation ")
        gPhones.value = listOf()
        viewModelScope.launch {
            if(isConnected(application)) GroupsPhoneRepository.getAll(forServer = true)
        }
    }

    fun insertGroupsPhone(gPh: GroupsPhone){
        viewModelScope.launch{
            GroupsPhoneRepository.insertGPhone(gPh)
        }
    }

    fun delete(gPh:GroupsPhone){
        viewModelScope.launch {
            GroupsPhoneRepository.delete(gPh)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            GroupsPhoneRepository.deleteAll()
        }
    }

}