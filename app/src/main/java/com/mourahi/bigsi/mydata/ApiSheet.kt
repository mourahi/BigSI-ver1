package com.mourahi.bigsi.mydata

import android.util.Log
import com.google.gson.JsonParser
import io.ktor.client.*
import io.ktor.client.request.*
//Get data from google sheet
object ApiSheet {
    private val client: HttpClient = HttpClient()

    suspend fun request(
        id:String,
        sheet:String="data",
        query:String = "SELECT *"
        ): List<List<String>> {

        val url ="https://docs.google.com/spreadsheets/d/$id/gviz/tq?tqx=out:json&sheet=$sheet&tq=$query"
       Log.d("adil","Server: sheet=$sheet query=$query id=$id")
        var vv:String= client.get(url)
        val ind = vv.indexOf("\"rows\":")
        val fin = vv.indexOf(",\"parsedNumHeaders\"")
        vv = if (fin > 0) {
            vv.substring(ind + 7, fin)
        } else {
            vv.substring(ind + 7)
        }
        val f = JsonParser.parseString(vv).asJsonArray
        val r = mutableListOf<MutableList<String>>()
        repeat(f.size()){ it ->
            if(f.get(it).isJsonObject){
            val x = f.get(it).asJsonObject.get("c").asJsonArray
            val item = mutableListOf<String>()
               repeat(x.size()){
                   if(x.get(it).isJsonObject) {
                       val xx = x.get(it).asJsonObject.get("v").toString()
                       item.add(
                           if (x.get(it).isJsonObject) if (xx.startsWith("\"") && xx.length > 2)
                               xx.removeSurrounding("\"", "\"") else xx else ""
                       )
                   }else {
                       item.add("")
                   }
               }
               r.add(item)
           }
        }
       return r
    }
}
