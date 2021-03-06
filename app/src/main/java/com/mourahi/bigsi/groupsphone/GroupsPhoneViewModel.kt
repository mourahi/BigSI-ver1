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
    val gPhonesInitial = GroupsPhoneRepository.allDataInitial

    init {
        Log.d("adil", "GroupsPhoneViewModel: Initialisation groupsPhoneRepo")
        gPhones.clear() // ici initial par value listof (peut etre cause erreur)
        viewModelScope.launch {
            GroupsPhoneRepository.getAll(false)
        }
    }

    fun getNbrChecked():String{
        val nbr = gPhones.size.toString()
        return gPhones.filter { it.isChecked }.size.toString()+ "/"+nbr
    }

    fun checkAll(check:Boolean =true ){
        gPhones.forEach {
            it.isChecked = check
        }
        viewModelScope.launch {  GroupsPhoneRepository.updateAll(gPhones) }
    }
    fun favAllList(l:List<GroupsPhone>,fav:Boolean=true){
        gPhones.forEach {
            it.isFav = fav
        }
        viewModelScope.launch {  GroupsPhoneRepository.updateAll(gPhones) }
    }

    fun insertGroupsPhone(gPh: GroupsPhone) {
        viewModelScope.launch{
         if(!gPh.isPersonnel) GroupsPhoneRepository.insertGPhone(gPh) else GroupsPhoneRepository.insertNewPersonelGroupsPhone(gPh)
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
    fun search(s:String){
        Log.d("adil","gphoneinitial = ${gPhonesInitial.size} gphones=${gPhones.size}")
        gPhones.clear()
        if(s.isNotEmpty()){
            val t = gPhonesInitial.filter { it.name.contains(s,true) }.toList()
            gPhones.addAll(t)
        }else {
            gPhones.addAll(gPhonesInitial.toList())
        }

    }

}