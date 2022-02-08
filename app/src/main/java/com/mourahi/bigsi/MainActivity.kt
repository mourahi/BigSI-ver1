package com.mourahi.bigsi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mourahi.bigsi.formation.FormationsPage
import com.mourahi.bigsi.forms.FormsPage
import com.mourahi.bigsi.groupsphone.GroupsPhonePage
import com.mourahi.bigsi.groupsphone.editgroupsphone.EditGroupsPhonePage
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
                        BigSI()
                    }
                }
            }
        }
    }
}

@Composable
private fun BigSI() {
    NavHost(navController = viewModelMain.navController , startDestination = "mainpage"){
        composable("mainpage"){ MainPage()}
        composable("groupsphonepage"){ GroupsPhonePage()}
        composable("newspage"){ NewsPage()}
        composable("formspage"){ FormsPage()}
        composable("phonespage/{IdSheet}", arguments = listOf(navArgument("IdSheet"){type= NavType.StringType})){
           val x:String = it.arguments?.get("IdSheet") as String
            PhonesPage(idSheet=x)
        }
        composable("formationspage"){ FormationsPage()}
        composable("editgroupsphonepage"){ EditGroupsPhonePage()}
        composable("detailsphone"){ DetailsPhone()}
    }
}
