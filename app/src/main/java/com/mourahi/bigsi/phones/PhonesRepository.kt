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
    private val allDataInitial = mutableStateListOf<Phone>()
    val allFavData = mutableStateListOf<Phone>()

    var activeGroupsPhone =GroupsPhone("","")
    var activePhone = Phone(nom = "", ecole = "", tel = "", cycle = "")

    val cats = mutableStateListOf<String>()
    val subCats = mutableStateListOf<String>()

    suspend fun getAll(idSheet: String,refGroup: Int,forServer:Boolean = false){
        Log.d("adil","demarrage Phone idsheet = $idSheet refGroup=$refGroup")

        if(refGroup == 0 || forServer){
            Log.d("adil","PHoneRepsoitory:getAll from  phonesFromServer")
            allData.clear();allDataInitial.clear()
            allData.addAll(phonesFromServer(idSheet,refGroup))
            allDataInitial.addAll(allData.toList())
            initialisation()
        }else{
            myDao.getAll(refGroup).collect {
                allData.clear();allDataInitial.clear()
                allData.addAll(it)
              //  Log.d("adil","je suis la ${allData.filter { a->a.isChecked}.size}")
                allDataInitial.addAll(allData.toList())
                initialisation()
            }
        }
    }

    suspend fun getAllFavPhone(){
        myDao.getAllFav().observeForever {
            allFavData.clear()
            allFavData.addAll(it)
        }
    }

    private suspend fun phonesFromServer(idSheet:String,refGroup: Int): List<Phone> {
        val a = ApiSheet.request(id = idSheet)
        val re = mutableListOf<Phone>()
        if (a.isNotEmpty()) {
            repeat(a.size) {
                val d = a[it]
            if(d[0]!="cycle")  re.add(Phone(d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8] ,false,refGroup))
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
private fun initialisation(){
    cats.clear() ; subCats.clear()
    cats.addAll(allDataInitial.map { it.cycle }.toSet())
    }

     fun filterByCatsAndSubCats(sCats:List<String>,subC: List<String>){
         if(sCats.isEmpty()) {
             subCats.clear()
         }
        if(allDataInitial.isNotEmpty()){
           var t =if(sCats.isNotEmpty())  allDataInitial.filter { it.cycle in sCats } else allDataInitial
            t= if(subC.isNotEmpty() && sCats.isNotEmpty()) t.filter { it.commune in subC } else t
            allData.clear()
            allData.addAll(t)
            subCats.clear()
            if(sCats.isNotEmpty())  subCats.addAll(allDataInitial.filter { it.commune.isNotEmpty() && it.cycle in sCats  }. map { it.commune }.toSet())
        }
    }

}