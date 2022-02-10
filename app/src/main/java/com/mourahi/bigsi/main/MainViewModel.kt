package com.mourahi.bigsi.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.mourahi.bigsi.mydata.MyRoomDB


class MainViewModel(application: Application) : AndroidViewModel(application) {
    //private val appContext: Context by lazy {  application.applicationContext }
    lateinit var navController:NavHostController
    val myDB: MyRoomDB by lazy { MyRoomDB.myDB(application) }


}