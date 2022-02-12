package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class PhonesViewModel:ViewModel() {
    val openPhoneDialog = mutableStateOf(false)
    val openGroupsDialog = mutableStateOf(false)
    val openCardOperations = mutableStateOf(false)

    val cats = mutableStateOf(listOf<String>())
    val catsSelected = mutableStateOf(mutableListOf<String>())
    val subCats = mutableStateOf(listOf<String>())
    val subCatsSelected = mutableStateOf(mutableListOf<String>())

    val phones = PhonesRepository.allData
    val idSheet = MutableStateFlow("")

     init{
        Log.d("adil","PhonesViewModel: Initialisation phoneRepo")
         phones.value = listOf()
         viewModelScope.launch {
             idSheet.collect {
                 if(it.isNotEmpty()){ // plus que 1 c'est sur idsheet d'un serveur
                     val p = it.split("*mourahi*")
                     Log.d("adil","Mon idsheet = $p egalite=${ p[0].toInt()== 0}")
                   PhonesRepository.getAll(p[1],forServer = p[0].toInt()==0,p[0].toInt())
                     updateCats()
                 }
             }
         }
    }

    private suspend fun updateCats(){
        Log.d("adil","updateListPhones")
        cats.value = PhonesRepository.getCats()
        subCats.value = PhonesRepository.getSubCats()
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
        viewModelScope.launch {
            PhonesRepository.updatePhone(ph)
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            PhonesRepository.deleteAll()
        }
    }

}