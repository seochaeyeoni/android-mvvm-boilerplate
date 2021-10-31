package com.alice.androidmvvm

import android.app.Application
import com.alice.androidmvvm.data.TodoDao
import com.alice.androidmvvm.data.TodoDatabase
import com.alice.androidmvvm.data.TodoRepositoryImpl

class AndroidApplication : Application() {

    companion object {
        lateinit var todoDao: TodoDao
        lateinit var todoRepository: TodoRepositoryImpl
        lateinit var mainViewModel: MainViewModel
    }

    override fun onCreate() {
        super.onCreate()

        // Dependency Injection
        todoDao = TodoDatabase.Factory.create(applicationContext).getDao()
        todoRepository = TodoRepositoryImpl(todoDao)
        mainViewModel = MainViewModel(todoRepository)
    }
}
