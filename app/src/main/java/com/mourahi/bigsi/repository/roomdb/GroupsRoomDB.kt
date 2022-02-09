package com.mourahi.bigsi.repository.roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mourahi.bigsi.groupsphone.GroupsPhone
import com.mourahi.bigsi.groupsphone.GroupsPhoneDao



@Database(entities = [GroupsPhone::class], version = 1, exportSchema = false)
abstract class GroupsRoomDB : RoomDatabase() {
    abstract fun myDao(): GroupsPhoneDao
    companion object {
        @Volatile
        private var INSTANCE: GroupsRoomDB? = null

        fun getDatabase(
            context: Context,
        ): GroupsRoomDB {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    GroupsRoomDB::class.java,
                    "room_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}