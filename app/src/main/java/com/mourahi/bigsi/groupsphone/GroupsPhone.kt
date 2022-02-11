package com.mourahi.bigsi.groupsphone

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "groups_phone")
 class GroupsPhone(
   @PrimaryKey(autoGenerate = true) var id:Int,
   @ColumnInfo var name:String,
   @ColumnInfo var region:String,
   @ColumnInfo var link:String,
   @ColumnInfo var isSaved:Boolean,
   @ColumnInfo var isFav:Boolean,
){
    constructor(name: String,region: String,link: String="",isSaved: Boolean=false,isFav: Boolean=false) :
            this(0,name,region,link,isSaved,isFav )
}

@Dao
 interface GroupsPhoneDao{
    @Query("SELECT * FROM groups_phone")
    fun getAll(): LiveData<List<GroupsPhone>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(gPh: GroupsPhone)

    @Query("DELETE FROM groups_phone WHERE id= :id")
    suspend fun delete(id:Int)

    @Query("DELETE FROM groups_phone")
    suspend fun deleteAll()
}

