package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    val activeGroupsPhone = PhonesRepository.activeGroupsPhone

     init{
        Log.d("adil","PhonesViewModel: Initialisation phoneRepo")
         phones.value = listOf()
         viewModelScope.launch {
             val activeGPH = PhonesRepository.activeGroupsPhone.value
             PhonesRepository.getAll(activeGPH.link,activeGPH.id)
             Log.d("adil","phones = ${PhonesRepository.allData.value}")

/*             idSheet.collect {
                 if(it.isNotEmpty()){ // plus que 1 c'est sur idsheet d'un serveur
                     val p = it.split("*mourahi*")
                     Log.d("adil","Mon idsheet = $p personal=${ p[0].toInt()== 0}")
                   PhonesRepository.getAll(p[1],forServer = p[0].toInt()==0,p[0].toInt())
                     Log.d("adil","groups depuis page phone = ${activeGroupsPhone.value}")
                     updateCats()
                 }
             }*/
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