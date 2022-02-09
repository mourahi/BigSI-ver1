package com.mourahi.bigsi.groupsphone

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "groups_phone")
data class GroupsPhone(
    @PrimaryKey(autoGenerate = true) var id:Int = 0,
    @ColumnInfo var name:String ="",
    @ColumnInfo var region:String="",
    @ColumnInfo var link:String="",
    @ColumnInfo var isSaved:Boolean = false,
    @ColumnInfo var isFav:Boolean =false,
)

@Dao
 interface GroupsPhoneDao{
    @Query("SELECT * FROM groups_phone")
    fun getAll(): LiveData<List<GroupsPhone>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(groupsPhone: GroupsPhone)

    @Query("DELETE FROM groups_phone")
    suspend fun deleteAll()
}

