package com.mourahi.bigsi.phones

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.mourahi.bigsi.mydata.ApiSheet
import com.mourahi.bigsi.viewModelMain

object PhonesRepository {
    private val myDao: PhoneDao by lazy { viewModelMain.myDB.myPhoneDao()}
    val allData = mutableStateOf(listOf<Phone>())


    suspend fun getAll(idSheet: String,forServer:Boolean= false,refgroup: Int=-1){
        if(forServer){
            allData.value = phonesFromServer(idSheet,refgroup= refgroup)
        }else{
            Log.d("adil","From Room refgroup=$refgroup")
            myDao.getAll(refgroup).observeForever {
                if(it != null ) allData.value = it else Log.d("adil","pas de données dans room")
            }
        }
    }


    private suspend fun phonesFromServer(idSheet:String,sheet:String="data",refgroup: Int = -1): List<Phone> {
        Log.d("adil", "idsheet=$idSheet")
        val a = ApiSheet.request(id = idSheet, sheet)
        val re = mutableListOf<Phone>()
        if (a.isNotEmpty()) {
            repeat(a.size) {
                val d = a[it]
                re.add(Phone(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8] ,false,refgroup))
            }
        }
        return re
    }

    suspend fun insertListPhonesFromGroupsPhone(idsheet:String,refgroup:Int){
        getAll(idsheet,true,refgroup)
        if(allData.value.isNotEmpty()){
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
        myDao.delete(ph.refgroup)
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