package com.mourahi.bigsi.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.mourahi.bigsi.phones.Phone

object Repo {
    private const val idGroupsPhone = "1__YWeJR26tpyCep99NETXyMi9lXe1MA3JiJWr4y2-n0"
    // Groups Phone ---------------------------------------------------------------
    //val groupsPhoneRepo = MutableLiveData<List<GroupsPhone>>()
    val phonesRepo = MutableLiveData<List<Phone>>()




    suspend fun updateListPhones(idSheet:String) {
        phonesRepo.value = phonesFromServer(idSheet)
    }

// fin groupsphone ----------------------------------------------

    private suspend fun phonesFromServer(idSheet:String,sheet:String="data"): List<Phone> {
        Log.d("adil", "idsheet=$idSheet")
        val a = HttpCall.request(id = idSheet, sheet)
        val re = mutableListOf<Phone>()
        if (a.isNotEmpty()) {
            repeat(a.size) {
                val d = a[it]
                re.add(Phone(-1, d[0], d[1], d[2], d[3], d[4], d[5], d[6], d[7], d[8]))
            }
        }
        return re
    }

// categories
    fun getCats(): List<String> {
        return listOf("Direction", "Lycee", "College", "Primaire", "Inspecteur", "Orientation")
    }

    fun getSubCats(): List<String> {
        return listOf("safi", "jamma", "seb", "hrara", "oulad salman", "moulbargue", "ayer")
    }

}