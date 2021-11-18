package com.alice.androidmvvm.ui.main

import androidx.lifecycle.ViewModel
import com.alice.androidmvvm.data.TodoRepositoryImpl
import com.alice.androidmvvm.model.TodoData
import com.alice.androidmvvm.model.TodoMainData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val todoRepository: TodoRepositoryImpl
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)
    val todoDataList: Flow<List<TodoMainData>> = todoRepository.getDataForMain()

    fun deleteTodoData(todoData: TodoData) {
        scope.launch {
            todoRepository.delete(todoData)
        }
    }
}
