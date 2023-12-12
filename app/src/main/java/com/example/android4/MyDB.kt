package com.example.android4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DBTodo::class], version = 1)
abstract class MyDB: RoomDatabase() {
    abstract fun myDao(): MyDao?

    companion object {
        private var DB_INSTANCE: MyDB? = null

        @Synchronized
        open fun getDatabase(context: Context): MyDB? {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(context.applicationContext
                    ,MyDB::class.java, "todo_database")
                    .allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE
        }
    }
}