package com.alice.androidmvvm.data

import com.alice.androidmvvm.model.TodoData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TodoRepositoryImpl @Inject constructor(
    private val todoDao: TodoDao
) : Repository<TodoData> {
    override suspend fun insert(data: TodoData) = todoDao.insert(data)

    override suspend fun update(data: TodoData) = todoDao.update(data)

    override suspend fun delete(data: TodoData) = todoDao.delete(data)

    override fun getData(): Flow<List<TodoData>> = todoDao.getAllData()
}
