package com.mourahi.bigsi.phones

import androidx.lifecycle.LiveData
import androidx.room.*

@Entity(tableName = "phone")
data class Phone(
    @PrimaryKey(autoGenerate = true)  @ColumnInfo var ref:Int,
    @ColumnInfo var cycle: String,
    @ColumnInfo var commune: String,
    @ColumnInfo var gresa: String,
    @ColumnInfo var ecole: String,
    @ColumnInfo var nom: String,
    @ColumnInfo var tel: String,
    @ColumnInfo var fonction: String,
    @ColumnInfo var email: String,
    @ColumnInfo var geo: String,
    @ColumnInfo var fav: Boolean,
    @ColumnInfo var refgroup: Int,
){
    constructor(cycle:String="",commune: String="",gresa: String="",ecole: String="",nom: String="",
    tel: String="",fonction: String="",email: String="",geo: String="",fav: Boolean=false,refgroup: Int=-1
                ):this(0,cycle,commune,gresa,ecole,nom,tel,fonction,email,geo,fav,refgroup)
}

@Dao
interface PhoneDao{
    @Query("SELECT * FROM phone WHERE refgroup = :refgroup")
    fun getAll(refgroup: Int): LiveData<List<Phone>>

    @Insert
    suspend fun insert(ph: Phone)

    @Insert
    suspend fun insertList(phs:List<Phone>)

    @Query("DELETE FROM phone WHERE refgroup= :refgroup")
    suspend fun delete(refgroup: Int)

    @Update
    suspend fun update(ph: Phone)

    @Query("DELETE FROM phone")
    suspend fun deleteAll()
}
