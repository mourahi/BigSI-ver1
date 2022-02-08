package com.mourahi.bigsi.phones.detailsphone

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.mourahi.bigsi.ui.theme.myPadding
import com.mourahi.bigsi.viewModelMain

@Composable
fun DetailsPhone(){
   Column(
       Modifier
           .fillMaxSize()
           .padding(myPadding)) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(myPadding))
        {
            Column(Modifier.align(Alignment.CenterStart)){
                IconButton(onClick = { viewModelMain.navController.popBackStack()}) {
                    Icon(Icons.Default.ArrowForward, contentDescription = "return",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Phone, contentDescription = "PHone",
                        tint = Color.Black
                    )
                }

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Email, contentDescription = "email",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Send, contentDescription = "sms",
                        tint = Color.Black
                    )
                }
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(Icons.Default.Share, contentDescription = "sms",
                        tint = Color.Black
                    )
                }

            }//fin column right
            Column(Modifier.align(Alignment.Center)) {
                Text("ثا. اع احمد الطيب بنهيمة")
                Text("0666667788")
                Text("احمد سبا")
                Text("جماعة اسفي")
                Text("كود كريزا")
            }
        }//fin box
    }
}