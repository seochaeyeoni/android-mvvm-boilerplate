package com.alice.androidmvvm.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alice.androidmvvm.data.TodoRepositoryImpl
import com.alice.androidmvvm.model.TodoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val todoRepository: TodoRepositoryImpl
) : ViewModel() {
    private val scope = CoroutineScope(Dispatchers.IO)

    private var _todoData = MutableLiveData<TodoData>()
    val todoData: LiveData<TodoData> get() = _todoData

    fun getTodoData(date: Int) {
        scope.launch {
            _todoData.postValue(todoRepository.getDataFromKey(date))
        }
    }

    fun insertTodoData(todoData: TodoData) {
        scope.launch {
            todoRepository.insert(todoData)
        }
    }

    fun updateTodoData(todoData: TodoData) {
        scope.launch {
            todoRepository.update(todoData)
        }
    }
}
