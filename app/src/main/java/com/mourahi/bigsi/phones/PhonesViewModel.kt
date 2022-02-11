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

    //val gPhones = mutableStateOf(listOf<GroupsPhone>())
    val cats = mutableStateOf(listOf<String>())
    val catsSelected = mutableStateOf(mutableListOf<String>())
    val subCats = mutableStateOf(listOf<String>())
    val subCatsSelected = mutableStateOf(mutableListOf<String>())

    val phones = mutableStateOf(listOf<Phone>())
    val idSheet = MutableStateFlow("")

     init{
        Log.d("adil","PhonesViewModel: Initialisation phoneRepo")
         viewModelScope.launch {
             idSheet.collect {
                if(it.isNotEmpty()) updateListPhones()
             }
         }
    }

    private suspend fun updateListPhones(){
        Log.d("adil","updateListPhones")
        cats.value = Repo.getCats()
        subCats.value = Repo.getSubCats()
            Repo.updateListPhones(idSheet.value)
            phones.value = Repo.phonesRepo.value!!
    }

}