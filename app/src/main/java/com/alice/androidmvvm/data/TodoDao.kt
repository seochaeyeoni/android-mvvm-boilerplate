package com.alice.androidmvvm.data

import androidx.room.*
import com.alice.androidmvvm.model.TodoData
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Query("SELECT * FROM TodoData ORDER BY dateTime ASC")
    fun getAllData(): Flow<List<TodoData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg todo: TodoData)

    @Update
    fun update(vararg todo: TodoData)

    @Delete
    fun delete(vararg todo: TodoData)
}
