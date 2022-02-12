package com.mourahi.bigsi.groupsphone

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "groups_phone")
 class GroupsPhone(
   @PrimaryKey(autoGenerate = true) var id:Int,
   @ColumnInfo var name:String,
   @ColumnInfo var region:String,
   @ColumnInfo var link:String,
   @ColumnInfo var isSavedFromServer:Boolean,
   @ColumnInfo var isFav:Boolean,
   @ColumnInfo var isChecked:Boolean=false,
){
    constructor(name: String,region: String,link: String="",isSavedFromServer: Boolean=false,isFav: Boolean=false,isChecked: Boolean=false) :
            this(0,name,region,link,isSavedFromServer,isFav,isChecked )
}

@Dao
 interface GroupsPhoneDao{
    @Query("SELECT * FROM groups_phone")
    fun getAll(): LiveData<List<GroupsPhone>>

    @Insert
    suspend fun insert(gPh: GroupsPhone):Long

    @Query("DELETE FROM groups_phone WHERE link= :link")
    suspend fun delete(link: String)

    @Update
    suspend fun update(gPh: GroupsPhone)

    @Update
    suspend fun updateAll(l:List<GroupsPhone>)

    @Query("DELETE FROM groups_phone")
    suspend fun deleteAll()
}

