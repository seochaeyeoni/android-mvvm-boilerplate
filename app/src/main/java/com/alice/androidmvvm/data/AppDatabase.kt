package com.alice.androidmvvm.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.alice.androidmvvm.model.TodoData
import com.alice.androidmvvm.util.Converters

@Database(entities = [TodoData::class], version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao
}
