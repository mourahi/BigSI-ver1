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

    init {
        Log.d("adil", "GroupsPhoneViewModel: Initialisation groupsPhoneRepo")
        gPhones.value = listOf()
        viewModelScope.launch {
            GroupsPhoneRepository.getAll(false)
        }
    }

    fun getNbrChecked():String{
        val nbr = gPhones.value.size.toString()
        return gPhones.value.filter { it.isChecked }.size.toString()+ "/"+nbr
    }

    fun checkAll(check:Boolean =true ){
        gPhones.value.forEach {
            it.isChecked = check
        }
        viewModelScope.launch {  GroupsPhoneRepository.updateAll(gPhones.value) }
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
    fun updateList(gphs:List<GroupsPhone>){
        viewModelScope.launch {
            GroupsPhoneRepository.updateAll(gphs)
        }
    }

    fun deleteList(l:List<GroupsPhone>){
        viewModelScope.launch {
            GroupsPhoneRepository.deleteList(l)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            GroupsPhoneRepository.deleteAll()
        }
    }

}