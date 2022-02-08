package com.mourahi.bigsi

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController

class MainViewModel(application: Application) : AndroidViewModel(application) {
    val appContext: Context by lazy {  application.applicationContext }
    lateinit var navController:NavHostController



}