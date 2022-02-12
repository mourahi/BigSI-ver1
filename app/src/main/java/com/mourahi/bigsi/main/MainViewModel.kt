package com.mourahi.bigsi.main

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.mourahi.bigsi.components.isConnected
import com.mourahi.bigsi.mydata.MyRoomDB


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val appContext: Context by lazy {  application.applicationContext }
    lateinit var navController:NavHostController
    val myDB: MyRoomDB by lazy { MyRoomDB.myDB(application) }

    fun isCo(): Boolean {
        return isConnected(appContext)
    }
    fun toast(s:String){
        Toast.makeText(appContext,s,Toast.LENGTH_LONG).show()
    }
}