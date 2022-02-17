package com.mourahi.bigsi.groupsphone

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.mourahi.bigsi.mydata.ApiSheet
import com.mourahi.bigsi.phones.PhonesRepository
import com.mourahi.bigsi.viewModelMain

object GroupsPhoneRepository {
    private const val idGroupsPhone = "1__YWeJR26tpyCep99NETXyMi9lXe1MA3JiJWr4y2-n0"
    private val myDao:GroupsPhoneDao by lazy { viewModelMain.myDB.myGroupsDao()  }

    val allData = mutableStateListOf<GroupsPhone>()

    val allDataDistant = mutableStateListOf<GroupsPhone>()

     suspend fun getAll(forServer:Boolean= false){
         if(forServer){
             allDataDistant.clear()
              allDataDistant.addAll(groupsPhoneFromServer())
         }else{
             myDao.getAll().observeForever {
                 Log.d("adil","ObserveForever hhhh")
                 allData.clear()
                 if(it != null ) allData.addAll(it)
                 else Log.d("adil","pas de données dans room")
             }
         }
     }

    private suspend fun groupsPhoneFromServer(): List<GroupsPhone> {
        val a = ApiSheet.request(id = idGroupsPhone, "groupe")
        val re = mutableListOf<GroupsPhone>()
        if (a.isNotEmpty()) {
            val f= allData.filter { i->i.isSavedFromServer }
            val fLink=f.map { ii->ii.link }
            repeat(a.size) {
                val d = a[it]
                val index = fLink.indexOf(d[2])
                val gPh =  if(index>-1) // -1 de teste et pas pour id hhhhh
                          GroupsPhone(id = f[index].id,d[0], d[1], d[2], index > -1,f[index].isFav)
                          else  GroupsPhone(d[0], d[1], d[2], index > -1)

                 re.add(gPh)
            }
        }
        return re
    }

    suspend fun insertGPhone(gPh: GroupsPhone) { // save GroupsPhone + Phones
        Log.d("adil","2/ GroupsPhoneRepository id= ${gPh.id} ref=${gPh.link}")//todo:-----------------------
        val refgroup = myDao.insert(gPh).toInt()
        Log.d("adil","fin insert GPH, et essaie d'inserer PHONES AVEC refGroup=$refgroup")
/*        val data:MutableList<GroupsPhone> = allDataDistant.value as MutableList<GroupsPhone>

        val i = data.indexOf(gPh)
        gPh.id = refgroup
        data[i] = gPh*/
       // allDataDistant.value

        PhonesRepository.insertListPhonesFromGroupsPhone(gPh.link,refgroup)
        Log.d("adil","fin insertListPhonesFromGroupsPhone")
    }

    suspend fun insertNewPersonelGroupsPhone(gPh: GroupsPhone){
        myDao.insert(gPh)
    }

    suspend fun updateGphone(gPh: GroupsPhone){
        Log.d("adil","update gph ${gPh.name} isFav=${gPh.isFav} ")
        myDao.update(gPh)
    }
    suspend fun updateAll(l:List<GroupsPhone>){
        myDao.updateList(l)
    }

    suspend fun delete(gPh:GroupsPhone){
        Log.d("adil","delete from room id=${gPh.id}")
        myDao.delete(gPh.link)
        PhonesRepository.deleteFromGroupsPhone(gPh.id)
    }

    suspend fun deleteList(l:List<GroupsPhone>){
        myDao.deleteList(l)
        PhonesRepository.deleteFromGroupsPhoneList( l.map { it.id })
    }

    suspend fun deleteAll(){
        Log.d("adil","delete all")
        myDao.deleteAll()
        PhonesRepository.deleteAll()
    }

     fun getGPhone(id:Int): GroupsPhone {
      return  myDao.getById(id)
    }


}