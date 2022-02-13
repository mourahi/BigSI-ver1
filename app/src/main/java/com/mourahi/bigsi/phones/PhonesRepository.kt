package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.mydata.ApiSheet
import com.mourahi.bigsi.viewModelMain

object PhonesRepository {
    private val myDao: PhoneDao by lazy { viewModelMain.myDB.myPhoneDao()}
    val allData = mutableStateOf(listOf<Phone>())
    val activeGroupsPhone: MutableState<GroupsPhone> = mutableStateOf(GroupsPhone("",""))


    suspend fun getAll(idSheet: String,refGroup: Int,forServer:Boolean = false){
        Log.d("adil","demarrage Phone idsheet = $idSheet refGroup=$refGroup")

        if(refGroup == 0 || forServer){
            allData.value = phonesFromServer(idSheet,refGroup)
        }else{
            Log.d("adil","je cherche dans ROOM")
            myDao.getAll(refGroup).observeForever {
                if(it != null ) allData.value = it else Log.d("adil","pas de données dans room")
            }
        }
    }

    private suspend fun phonesFromServer(idSheet:String,refGroup: Int): List<Phone> {
        val a = ApiSheet.request(id = idSheet)
        val re = mutableListOf<Phone>()
        if (a.isNotEmpty()) {
            repeat(a.size) {
                val d = a[it]
                re.add(Phone(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8] ,false,refGroup))
            }
        }
        return re
    }

    suspend fun insertListPhonesFromGroupsPhone(idSheet:String,refGroup:Int){
        Log.d("adil","PhoneRepository: insertListPhonesFromGroupsPhone refGroud=$refGroup , idsheet=$idSheet")
        getAll(idSheet,refGroup,forServer = true) // must be true to indicate that we need for server
        Log.d("adil","sauvegarde idsheet=$idSheet , refGRoups=$refGroup")

        if(allData.value.isNotEmpty()){
            Log.d("adil", "alldata = ${allData.value}")
            allData.value.forEach {
                Log.d("adil","id= ${it.refgroup}")
            }
            myDao.insertList(allData.value)
        }
    }

    suspend fun insertPhone(ph: Phone) {
        Log.d("adil","je suis dans insertphONE")
        myDao.insert(ph)
    }

    suspend fun updatePhone(ph: Phone){
        Log.d("adil","update ph ${ph.ecole} isFav=${ph.fav} ")
        myDao.update(ph)
    }

    suspend fun delete(ph: Phone){
        Log.d("adil","delete from room id=${ph.ref}")
        myDao.deleteByRefgroup(ph.refgroup)
    }
    suspend fun deleteFromGroupsPhone(refGroup: Int){
        myDao.deleteByRefgroup(refGroup)
    }
    suspend fun deleteFromGroupsPhoneList(l:List<Int>){
     l.forEach { myDao.deleteByRefgroup(it) }
    }

    suspend fun deleteAll(){
        Log.d("adil","delete all")
        myDao.deleteAll()
    }
    
    
    
    
    
// categories
    fun getCats(): List<String> {
        return listOf("Direction", "Lycee", "College", "Primaire", "Inspecteur", "Orientation")
    }

    fun getSubCats(): List<String> {
        return listOf("safi", "jamma", "seb", "hrara", "oulad salman", "moulbargue", "ayer")
    }

}