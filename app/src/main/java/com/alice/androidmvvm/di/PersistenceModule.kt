package com.alice.androidmvvm.di

import android.app.Application
import androidx.room.Room
import com.alice.androidmvvm.data.AppDatabase
import com.alice.androidmvvm.data.TodoDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(
            application,
            AppDatabase::class.java,
            "Todo.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoDao(appDatabase: AppDatabase): TodoDao {
        return appDatabase.todoDao()
    }
}
