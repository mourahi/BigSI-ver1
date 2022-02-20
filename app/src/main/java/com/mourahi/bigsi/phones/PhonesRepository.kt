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
    val allDataInitial = mutableStateListOf<Phone>()

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
            initCatsAnsSubCats()
        }else{
            myDao.getAll(refGroup).collect {
                allData.clear();allDataInitial.clear()
                allData.addAll(it)
                Log.d("adil","je suis la ${allData.filter { a->a.isChecked}.size}")
                initCatsAnsSubCats()
            }
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
private fun initCatsAnsSubCats(){
    cats.clear()
    cats.addAll(allData.map { it.cycle }.toSet())
    }

    suspend fun filterByCatsAndSubCats(sCats:List<String>){
        if(allDataInitial.isNullOrEmpty()){
            allDataInitial.addAll( myDao.getByRefGroup(activeGroupsPhone.id))
        }
        allData.clear()
        val t = allDataInitial.filter { sCats.contains(it.cycle)}.toList()
        if(t.isNotEmpty()) allData.addAll(t) else allData.addAll(allDataInitial)
        updateSubCats(sCats)
    }

    suspend fun filterBySubCats(sCats:List<String>, subCats:List<String>){
        val t = allData.filter { subCats.contains(it.commune) }.toList()
        if(t.isEmpty()) filterByCatsAndSubCats(sCats) else allData.clear();allData.addAll(t)
    }


    private fun updateSubCats(sCats:List<String>) {
        subCats.clear()
      if(sCats.isNotEmpty())  subCats.addAll(allData.map { it.commune }.toSet())
    }

}