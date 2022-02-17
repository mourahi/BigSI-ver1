package com.mourahi.bigsi.dynamic

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DynamicViewModel(application: Application):AndroidViewModel(application){
    val data = DynamicRepository.data
    val cats =DynamicRepository.cats
    val a =DynamicRepository.a
    init {
        viewModelScope.launch {
            delay(3000)
            DynamicRepository.testInitData(listOf("un","deux","trois"))
            delay(3000)
            DynamicRepository.testInitData(listOf("un","deux","trois"))
            delay(3000)
            DynamicRepository.testInitData(listOf("un","deux","trois"))
        }
    }

    fun addCards(){
        DynamicRepository.testInitData(listOf("ad","mo","fa","ca"))
    }
    fun addToa(){
        DynamicRepository.addtoA()
    }

}