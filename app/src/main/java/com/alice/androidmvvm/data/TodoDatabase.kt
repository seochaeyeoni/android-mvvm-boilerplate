package com.alice.androidmvvm.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.alice.androidmvvm.model.TodoData

@Database(entities = [TodoData::class], version = 1)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getDao(): TodoDao
    object Factory {
        private const val dbName = "todo.db"
        fun create(context: Context): TodoDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                dbName
            ).build()
        }
    }
}
