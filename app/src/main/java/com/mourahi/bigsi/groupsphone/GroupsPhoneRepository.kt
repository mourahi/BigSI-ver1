package com.mourahi.bigsi.groupsphone

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.mourahi.bigsi.mydata.ApiSheet
import com.mourahi.bigsi.viewModelMain

object GroupsPhoneRepository {
    private const val idGroupsPhone = "1__YWeJR26tpyCep99NETXyMi9lXe1MA3JiJWr4y2-n0"
    private val myDao:GroupsPhoneDao by lazy { viewModelMain.myDB.myGroupsDao()  }
    val allData = mutableStateOf(listOf<GroupsPhone>())
    val allDataDistant = mutableStateOf(listOf<GroupsPhone>())

     suspend fun getAll(forServer:Boolean= false){
         if(forServer){
              allDataDistant.value = groupsPhoneFromServer()
         }else{
             myDao.getAll().observeForever {
                 if(it != null ) allData.value = it else Log.d("adil","pas de données dans room")
             }
         }
     }

    private suspend fun groupsPhoneFromServer(): List<GroupsPhone> {
        val a = ApiSheet.request(id = idGroupsPhone, "groupe")
        val re = mutableListOf<GroupsPhone>()
        if (a.isNotEmpty()) {
            val f= allData.value.filter { i->i.isSaved }
            val fName=f.map { ii->ii.name }
            val fRegion = f.map{iii->iii.region}
            repeat(a.size) {
                val d = a[it]
                val ok= fName.contains(d[1]) && fRegion.contains(d[2])
                if(!ok) re.add(GroupsPhone( d[1], d[2], d[3]))
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