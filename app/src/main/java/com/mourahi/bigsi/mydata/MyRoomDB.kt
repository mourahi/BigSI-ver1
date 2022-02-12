package com.mourahi.bigsi.mydata

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.groupsphone.GroupsPhoneDao
import com.mourahi.bigsi.phones.Phone
import com.mourahi.bigsi.phones.PhoneDao

@Database(entities = [GroupsPhone::class,Phone::class], version = 1, exportSchema = false)
abstract class MyRoomDB : RoomDatabase() {
    abstract fun myGroupsDao(): GroupsPhoneDao
    abstract fun myPhoneDao():PhoneDao

    companion object {
        @Volatile
        private var INSTANCE: MyRoomDB? = null

        fun myDB(
            context: Context,
        ): MyRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MyRoomDB::class.java,
                    "bigsi-db-v9"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}