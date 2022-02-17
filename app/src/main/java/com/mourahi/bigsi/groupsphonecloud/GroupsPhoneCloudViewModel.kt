package com.mourahi.bigsi.groupsphonecloud

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.groupsphone.GroupsPhoneRepository
import com.mourahi.bigsi.viewModelMain
import kotlinx.coroutines.launch

class GroupsPhoneCloudViewModel() : ViewModel() {
    val gPhones =GroupsPhoneRepository.allDataDistant

    init {
        Log.d("adil", "GroupsPhoneCloudViewModel: Initialisation ")
        gPhones.add(GroupsPhone("",""))
        viewModelScope.launch {
            if(viewModelMain.isCo()) GroupsPhoneRepository.getAll(forServer = true)
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

    fun deleteAll(){
        viewModelScope.launch {
            GroupsPhoneRepository.deleteAll()
        }
    }

}