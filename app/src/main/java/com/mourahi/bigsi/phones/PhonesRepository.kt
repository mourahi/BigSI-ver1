package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.mydata.ApiSheet
import com.mourahi.bigsi.viewModelMain
import kotlinx.coroutines.flow.collect

object PhonesRepository {
    private val myDao: PhoneDao by lazy { viewModelMain.myDB.myPhoneDao()}
    val allData = mutableStateListOf<Phone>()
    var activeGroupsPhone =GroupsPhone("","")

    val cats = mutableStateListOf<String>()


    suspend fun getAll(idSheet: String,refGroup: Int,forServer:Boolean = false){
        Log.d("adil","demarrage Phone idsheet = $idSheet refGroup=$refGroup")

        if(refGroup == 0 || forServer){
            Log.d("adil","PHoneRepsoitory:getAll from  phonesFromServer")
            allData.clear()
            allData.addAll( phonesFromServer(idSheet,refGroup))
        }else{
            myDao.getAll(refGroup).collect {
                allData.clear()
                allData.addAll(it)
                Log.d("adil","je suis la ${allData.filter { a->a.isChecked}.size}")
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
        getAll(idSheet,refGroup,forServer = true) // must be true to indicate that we need for server
        Log.d("adil","sauvegarde idsheet=$idSheet , refGRoups=$refGroup")

        if(allData.isNotEmpty()){
            Log.d("adil", "alldata = $allData")
            allData.forEach {
                Log.d("adil","id= ${it.refgroup}")
            }
            myDao.insertList(allData)
        }
    }

    suspend fun insertPhone(ph: Phone) {
        Log.d("adil","je suis dans insertphONE")
        myDao.insert(ph)
    }

    suspend fun updatePhone(ph: Phone){
        Log.d("adil","update ph ${ph.ecole} isFav=${ph.fav} id:${ph.ref} ")
        myDao.update(ph)
    }
    suspend fun updateList(phs: List<Phone>){
        Log.d("adil","updateList for repsository")
        myDao.updateList(phs)
    }

    suspend fun checkAll(check:Boolean,refGroup:Int){
        myDao.checkAll(check,refGroup)
    }

    suspend fun delete(ph: Phone){
        Log.d("adil","delete from room id=${ph.ref}")
        myDao.deleteByRefgroup(ph.refgroup)
    }

    suspend fun deleteList(phs:List<Phone>){
            myDao.deleteList(phs.map { it.ref })
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