package com.mourahi.bigsi.dynamic

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Card
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun DynamicPage(vm: DynamicViewModel= viewModel()){
    Scaffold(
        topBar = {
            TopAppBar(
                title ={Text("nbr:")}
            )
        }
    ) {
        Column(modifier = Modifier.fillMaxSize()){

                DynamicCats(vm.cats) { Log.d("adil","cats click")}
                DynamicCardOperations(vm.a){Log.d("adil","cardOper click");vm.addToa()}
                DynamicCards(vm.data){ Log.d("adil","cards click");vm.addCards() }

        }

    }
}

@Composable
fun DynamicCats(input:List<String>,output:()->Unit){
Card(modifier = Modifier
    .fillMaxWidth()
    .border(1.dp, Color.Green)
    .clickable { Log.d("adil", "click sur DynamicCats ") } , backgroundColor = Color.Yellow) {
    Log.d("adil","1/ init DynamicCats")
    Text(text = "DynamicCats input=${input.size}")
}
}
@Composable
fun DynamicCardOperations(input:List<String>,output: () -> Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Green) , backgroundColor = Color.Yellow) {
        Log.d("adil","2/ init DynamicCardOperations")
        Text(text = "DynamicCardOperations a=$input",Modifier.clickable { output() })
    }
}
@Composable
fun DynamicCards(input:List<String>,output:()->Unit){
    Card(modifier = Modifier
        .fillMaxWidth()
        .border(1.dp, Color.Green) , backgroundColor = Color.Yellow) {
        Log.d("adil","3/init DynamicCards ${input.size}")
        Text(text = "DynamicCards input=$input", modifier = Modifier.clickable {
            output()
        })
    }
    input.forEach {
        Text("cards item = $it")
    }
}