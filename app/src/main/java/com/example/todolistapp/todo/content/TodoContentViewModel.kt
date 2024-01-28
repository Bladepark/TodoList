package com.example.todolistapp.todo.content

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todolistapp.todo.data.TodoContentType
import com.example.todolistapp.todo.data.TodoModel

class TodoContentViewModel(
    private val entryType: TodoContentType,
    private val originTodoModel: TodoModel
) : ViewModel() {

    private val _contentUiState: MutableLiveData<TodoContentUiState> = MutableLiveData()
    val contentUiState: LiveData<TodoContentUiState> get() = _contentUiState

    init {
        if (entryType == TodoContentType.UPDATE) {
            _contentUiState.value = TodoContentUiState(
                todoModel = originTodoModel,
                btnVisible = true
            )
        }
    }
}

class TodoContentViewModelFactory(
    private val entryType: TodoContentType,
    private val todoModel: TodoModel?
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TodoContentViewModel::class.java)) {
            return TodoContentViewModel(
                entryType,
                todoModel ?: TodoModel()
            ) as T
        } else {
            throw IllegalArgumentException("Not found ViewModel class.")
        }
    }
}
