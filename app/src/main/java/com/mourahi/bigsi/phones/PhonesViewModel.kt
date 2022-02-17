package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PhonesViewModel:ViewModel() {
    val openPhoneDialog = mutableStateOf(false)
    val openGroupsDialog = mutableStateOf(false)
    val openCardOperations = mutableStateOf(false)

    val cats = PhonesRepository.cats
    val catsSelected =  mutableStateListOf<String>()
    val subCats =  mutableStateListOf<String>()
    val subCatsSelected = mutableStateListOf<String>()

    val phones = PhonesRepository.allData

    val activeGroupsPhone = PhonesRepository.activeGroupsPhone
    var activePhone = Phone(nom = "", ecole = "", tel = "", cycle = "")

     init{
        Log.d("adil","PhonesViewModel: Initialisation phoneRepo")
         viewModelScope.launch {
             phones.clear()
             cats.clear()
             subCats.clear()
             updateCats()
             val activeGPH = PhonesRepository.activeGroupsPhone
             PhonesRepository.getAll(activeGPH.link,activeGPH.id)
         }
    }

    private  fun updateCats(){
        Log.d("adil","PhonesViewModel updateCats")
        cats.clear()
        cats.addAll(PhonesRepository.getCats())
        subCats.clear()
        subCats.addAll( PhonesRepository.getSubCats())
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
        viewModelScope.launch {
            PhonesRepository.updatePhone(ph)
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