package com.example.android4

import android.content.Context

class MyRepository(context: Context) {
    private var dataList: MutableList<DBTodo>? = null
    private var myDao: MyDao
    private var db: MyDB


    fun getData(): MutableList<DBTodo> {
        dataList = myDao.getAllData()
        return dataList as MutableList<DBTodo>
    }

    fun addItem(item: DBTodo): Boolean {
        return myDao.insert(item) > 0
    }

    fun deleteItem(item: DBTodo): Boolean {
        return myDao.delete(item) > 0
    }

    fun updateItem(item: DBTodo): Boolean {
        return myDao.update(item) > 0
    }



    companion object {
        private var R_INSTANCE: MyRepository? = null
        fun getInstance(context: Context): MyRepository {
            if (R_INSTANCE == null) {
                R_INSTANCE = MyRepository(context)
            }
            return R_INSTANCE as MyRepository
        }
    }

    init {
        db = MyDB.getDatabase(context)!!
        myDao = db.myDao()!!
    }
}