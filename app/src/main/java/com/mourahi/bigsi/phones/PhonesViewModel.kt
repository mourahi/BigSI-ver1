package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.groupsphone.GroupsPhoneRepository
import kotlinx.coroutines.launch

class PhonesViewModel:ViewModel() {
    val openPhoneDialog = mutableStateOf(false)
    val openGroupsDialog = mutableStateOf(false)
    val openCardOperations = mutableStateOf(false)

    val cats = PhonesRepository.cats
    val catsSelected =  mutableStateListOf<String>()
    val subCats =  PhonesRepository.subCats
    val subCatsSelected = mutableStateListOf<String>()

    val phones = PhonesRepository.allData
    val phonesInitial = PhonesRepository.allDataInitial

    var activeGroupsPhone = PhonesRepository.activeGroupsPhone
    var activePhone = PhonesRepository.activePhone

     init{
        Log.d("adil","PhonesViewModel: Initialisation phoneRepo")
         viewModelScope.launch {
             subCats.clear();cats.clear();phones.clear()
             val activeGPH = PhonesRepository.activeGroupsPhone
             PhonesRepository.getAll(activeGPH.link,activeGPH.id)
         }
    }

    fun filterByCatsAndSubCats(sCats:List<String>,type:String="cats"){
        viewModelScope.launch {
       PhonesRepository.filterByCatsAndSubCats(sCats)
        }
    }

    fun activePhone(ph:Phone){
        PhonesRepository.activePhone = ph
    }
    
    fun insertPhone(ph: Phone){
        viewModelScope.launch{
            PhonesRepository.insertPhone(ph)
        }
    }

    fun delete(ph: Phone){
        viewModelScope.launch {
            PhonesRepository.delete(ph)
        }
    }

    fun update(ph: Phone){
        Log.d("adil","update phone")
        PhonesRepository.activePhone = ph
        viewModelScope.launch {
            PhonesRepository.updatePhone(ph)
        }
    }
    fun updateGroupsPhoneFromPhonePage(ghp:GroupsPhone){
        viewModelScope.launch {
            GroupsPhoneRepository.updateGphone(ghp)
        }
    }

    fun deleteList(phs:List<Phone>){
        viewModelScope.launch {
            PhonesRepository.deleteList(phs)
        }
    }

    fun updateList(phs: List<Phone>){
        viewModelScope.launch {
            PhonesRepository.updateList(phs)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            PhonesRepository.deleteAll()
        }
    }

     fun checkAll(check:Boolean){
         viewModelScope.launch {
             phones.forEach { it.isChecked=check }
             PhonesRepository.checkAll(check,activeGroupsPhone.id)
         }
    }

}