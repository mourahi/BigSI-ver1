package com.mourahi.bigsi

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.mourahi.bigsi.repository.roomdb.GroupsRoomDB


class MainViewModel(application: Application) : AndroidViewModel(application) {
    //private val appContext: Context by lazy {  application.applicationContext }
    lateinit var navController:NavHostController
    val myDB:GroupsRoomDB by lazy { GroupsRoomDB.getDatabase(application) }


}