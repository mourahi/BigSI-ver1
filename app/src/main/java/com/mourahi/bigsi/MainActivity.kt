package com.mourahi.bigsi

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.shouldShowRationale
import com.mourahi.bigsi.dynamic.DynamicPage
import com.mourahi.bigsi.formation.FormationsPage
import com.mourahi.bigsi.forms.FormsPage
import com.mourahi.bigsi.groupsphone.GroupsPhonePage
import com.mourahi.bigsi.groupsphonecloud.GroupsPhoneCloudPage
import com.mourahi.bigsi.main.MainPage
import com.mourahi.bigsi.main.MainViewModel
import com.mourahi.bigsi.news.NewsPage
import com.mourahi.bigsi.phones.PhonesPage
import com.mourahi.bigsi.phones.detailsphone.DetailsPhone
import com.mourahi.bigsi.ui.theme.BigSIver1Theme


lateinit var viewModelMain: MainViewModel
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BigSIver1Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    viewModelMain = viewModel()
                    viewModelMain.navController = rememberNavController()
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl ) {
                        Column{
                            BigSI()
                            MyPermission()
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun BigSI() {
    NavHost(navController = viewModelMain.navController , startDestination = "mainpage"){
        composable("mainpage"){ MainPage() }
        composable("groupsphonepage"){ GroupsPhonePage()}
        composable("newspage"){ NewsPage()}
        composable("formspage"){ FormsPage()}
        composable("phonespage"){ PhonesPage()}
        composable("formationspage"){ FormationsPage()}
        composable("detailsphone"){ DetailsPhone()}
        composable("groupscloudpage"){ GroupsPhoneCloudPage() }
        composable("dynamicpage"){ DynamicPage() }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MyPermission(){
    val permissionsState = rememberMultiplePermissionsState (listOf(
        Manifest.permission.CALL_PHONE,
        Manifest.permission.CAMERA
    ))

    permissionsState.permissions.forEach { perm->
        when(perm.permission){
            Manifest.permission.CALL_PHONE ->{
               Log.d("adil","call phone ${perm.status}")
                when{
                    perm.status.shouldShowRationale -> {
                        Log.d("adil","ok it's should hhhh")
                        SideEffect {
                            permissionsState.launchMultiplePermissionRequest()
                        }
                    }
                    perm.status.isGranted -> {
                        Log.d("adil","is garantie")
                    }
                    !perm.status.isGranted -> {
                        Log.d("adil","non garantie")
                        SideEffect {
                            permissionsState.launchMultiplePermissionRequest()
                        }

                    }
                }
            }
            Manifest.permission.CAMERA -> {}
        }

    }
}
