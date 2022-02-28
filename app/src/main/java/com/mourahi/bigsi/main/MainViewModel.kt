package com.mourahi.bigsi.main

import android.app.Application
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.mourahi.bigsi.components.isConnected
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.groupsphone.GroupsPhoneRepository
import com.mourahi.bigsi.mydata.MyRoomDB
import kotlinx.coroutines.launch


class MainViewModel(application: Application) : AndroidViewModel(application) {
    private val pm:PackageManager = application.packageManager
    private val appContext: Context by lazy {  application.applicationContext }
    lateinit var navController:NavHostController
    val myDB: MyRoomDB by lazy { MyRoomDB.myDB(application) }

    val allFavGroupsPhone:List<GroupsPhone> by lazy {
            viewModelScope.launch {GroupsPhoneRepository.getAll(false) }
             GroupsPhoneRepository.allFavData
    }

    fun updateGroupsPhone(gPh:GroupsPhone){
        viewModelScope.launch {
            GroupsPhoneRepository.updateGphone(gPh)
        }
    }


    fun isCo(): Boolean {
        return isConnected(appContext)
    }
    fun toast(s:String){
        Toast.makeText(appContext,s,Toast.LENGTH_LONG).show()
    }

    fun call(tel:String){
        val intent = Intent(Intent.ACTION_CALL)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.data = Uri.parse("tel:$tel")
        appContext.startActivity(intent)
    }
    fun sendSMS(tel:String){
        Log.d("adil","depuis MAINVEIWMODEL SEND SMS TO $tel")

        val uri = Uri.parse("smsto:$tel")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.putExtra("sms_body", "Here goes your message...")
        appContext.startActivity(intent)
    }
    fun sendMail(mail:String){
        Log.d("adil","depuis MAINVEIWMODEL SEND MAIL TO $mail")
        val intent = Intent(Intent.ACTION_SENDTO).apply {
            data =  Uri.parse("mailto:")
            putExtra(Intent.EXTRA_EMAIL, arrayOf(mail,"adil.mourahi@gmail.com"))
            putExtra(Intent.EXTRA_SUBJECT,"UN SUJET ICI")
            putExtra(Intent.EXTRA_TEXT,"text un deux trois")
            flags =  Intent.FLAG_ACTIVITY_NEW_TASK
            type ="text/plain"
        }
        if(intent.resolveActivity(pm)  != null){
            appContext.startActivity(intent)
        }else {
            Log.d("adil","je ne peux pas envoyer mail")
        }

    }


}