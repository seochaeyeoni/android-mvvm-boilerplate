package com.alice.androidmvvm.di

import com.alice.androidmvvm.data.TodoDao
import com.alice.androidmvvm.data.TodoRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped

@Module
@InstallIn(ActivityRetainedComponent::class)
object RepositoryModule {

    @Provides
    @ActivityRetainedScoped
    fun provideTodoRepository(
        todoDao: TodoDao
    ): TodoRepositoryImpl {
        return TodoRepositoryImpl(todoDao)
    }
}
