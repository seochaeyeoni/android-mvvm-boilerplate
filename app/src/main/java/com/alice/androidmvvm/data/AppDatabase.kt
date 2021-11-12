package com.alice.androidmvvm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alice.androidmvvm.model.TodoData

@Database(entities = [TodoData::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
