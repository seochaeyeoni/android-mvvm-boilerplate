package com.alice.androidmvvm.data

import kotlinx.coroutines.flow.Flow

interface Repository<T> {
    suspend fun insert(data: T)
    suspend fun update(data: T)
    suspend fun delete(data: T)
    fun getData(): Flow<List<T>>
}
