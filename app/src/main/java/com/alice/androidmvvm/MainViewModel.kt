package com.alice.androidmvvm

import androidx.lifecycle.ViewModel
import com.alice.androidmvvm.data.TodoRepositoryImpl
import com.alice.androidmvvm.model.TodoData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val todoRepository: TodoRepositoryImpl) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)
    val todoData: Flow<List<TodoData>> = todoRepository.getData()
    val updateList = mutableMapOf<Long, TodoData>()

    fun insertTodoData(todoData: TodoData) {
        scope.launch {
            todoRepository.insert(todoData)
        }
    }

    fun updateTodoData(todoList: List<TodoData>) {
        scope.launch {
            todoList.forEach {
                todoRepository.update(it)
            }
        }
    }

    fun deleteTodoData(todoData: TodoData) {
        scope.launch {
            todoRepository.delete(todoData)
        }
    }

    fun setUpdateList(todoData: TodoData) {
        updateList[todoData.dateTime] = todoData
    }
}
