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
                 Log.d("adil","ObserveForever hhhh")
                 if(it != null ) allData.value = it else Log.d("adil","pas de données dans room")
             }
         }
     }

    private suspend fun groupsPhoneFromServer(): List<GroupsPhone> {
        val a = ApiSheet.request(id = idGroupsPhone, "groupe")
        val re = mutableListOf<GroupsPhone>()
        if (a.isNotEmpty()) {
            val f= allData.value.filter { i->i.isSavedFromServer }
            val fLink=f.map { ii->ii.link }
            repeat(a.size) {
                val d = a[it]
                val index = fLink.indexOf(d[2])
               val gPh = GroupsPhone( d[0], d[1], d[2],index>-1)
                 re.add(gPh)
            }
        }
        return re
    }

    suspend fun insertGPhone(gPh: GroupsPhone) {
        Log.d("adil","je suis dans insertGPHONE")
          myDao.insert(gPh)
    }

    suspend fun updateGphone(gPh: GroupsPhone){
        Log.d("adil","update gph ${gPh.name} isFav=${gPh.isFav} ")
        myDao.update(gPh)
    }

    suspend fun delete(gPh:GroupsPhone){
        Log.d("adil","delete from room id=${gPh.id}")
        myDao.delete(gPh.link)
    }

    suspend fun deleteAll(){
        Log.d("adil","delete all")
        myDao.deleteAll()
    }

}