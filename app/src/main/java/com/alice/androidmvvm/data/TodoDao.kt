package com.alice.androidmvvm.data

import androidx.room.*
import com.alice.androidmvvm.model.TodoData
import com.alice.androidmvvm.model.TodoMainData
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Query("SELECT * FROM TodoData ORDER BY date DESC")
    fun getAllData(): Flow<List<TodoData>>

    @Query("SELECT date, title FROM TodoData ORDER BY date DESC")
    fun getAllDataForMain(): Flow<List<TodoMainData>>

    @Query("SELECT * FROM TodoData WHERE date =:key")
    fun getDataFromKey(key: Int): TodoData

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insert(vararg todo: TodoData)

    @Update
    fun update(vararg todo: TodoData)

    @Delete
    fun delete(vararg todo: TodoData)
}
