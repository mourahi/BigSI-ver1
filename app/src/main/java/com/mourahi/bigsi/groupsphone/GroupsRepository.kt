package com.mourahi.bigsi.groupsphone

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.mourahi.bigsi.repository.HttpCall
import com.mourahi.bigsi.viewModelMain

object GroupsRepository {
    private const val idGroupsPhone = "1__YWeJR26tpyCep99NETXyMi9lXe1MA3JiJWr4y2-n0"
    private val myDao:GroupsPhoneDao by lazy { viewModelMain.myDB.myDao()  }
    val allData = mutableStateOf(listOf<GroupsPhone>())

     suspend fun getAll(forServer:Boolean= false){
         if(forServer){
             allData.value = groupsPhoneFromServer()
         }else{
             myDao.getAll().observeForever {
                 if(it != null ) allData.value = it else Log.d("adil","pad de données dans room")
             }
         }
     }

    private suspend fun groupsPhoneFromServer(): List<GroupsPhone> {
        val a = HttpCall.request(id = idGroupsPhone, "groupe")
        val re = mutableListOf<GroupsPhone>()
        if (a.isNotEmpty()) {
            repeat(a.size) {
                val d = a[it]
                re.add(GroupsPhone(-1, d[1], d[2], d[3]))
            }
        }
        return re
    }

    suspend fun insertGPhone(groupsPhone: GroupsPhone) {
        Log.d("adil","je suis dans insertGPHONE")
        myDao.insert(groupsPhone)
    }

    suspend fun deleteAll(){
        Log.d("adil","delete all")
        myDao.deleteAll()
    }
}