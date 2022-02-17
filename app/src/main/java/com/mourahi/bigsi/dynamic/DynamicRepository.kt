package com.mourahi.bigsi.dynamic

import androidx.compose.runtime.mutableStateListOf

object DynamicRepository{
        val data = mutableStateListOf<String>()
    val cats =mutableStateListOf<String>()
        val a = mutableStateListOf<String>()



    fun testInitData(d:List<String>) {
        data.addAll(d)
    }
    fun testInitCats(c:List<String>){
        cats.addAll(c)
    }
    fun addtoA(){
        a.addAll(listOf("aa","bb","cc","dd"))
    }

}