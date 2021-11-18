package com.alice.androidmvvm.di

import com.alice.androidmvvm.data.TodoDao
import com.alice.androidmvvm.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideTodoRepository(
        todoDao: TodoDao
    ): TodoRepositoryImpl {
        return TodoRepositoryImpl(todoDao)
    }
}
