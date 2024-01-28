package com.example.todolistapp.todo.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todolistapp.todo.data.TodoContentType
import com.example.todolistapp.todo.data.TodoModel

class TodoViewModel : ViewModel() {
    private val _uiState: MutableLiveData<TodoUiState> =
        MutableLiveData(TodoUiState.init())
    val uiState: LiveData<TodoUiState> get() = _uiState


    fun updateBookmarkStatus(todoModel: TodoModel?) {
        if (todoModel == null) {
            return
        }

        val updatedList = uiState.value?.list?.map {
            if (it.id == todoModel.id) {
                it.copy(isBookmarked = !it.isBookmarked)
            } else {
                it
            }
        }
        _uiState.value = uiState.value?.copy(list = updatedList.orEmpty())
    }

    private fun addTodoItem(
        model: TodoModel?
    ) {
        if (model == null) {
            return
        }
        _uiState.value = uiState.value?.copy(
            list = uiState.value?.list.orEmpty().toMutableList().apply {
                add(model)
            }
        )
    }

    private fun updateTodoItem(model: TodoModel?) {
        if (model == null) {
            return
        }

        val updatedList = uiState.value?.list?.map {
            if (it.id == model.id) {
                it.copy(
                    title = model.title,
                    description = model.description
                )
            } else {
                it
            }
        }
        _uiState.value = uiState.value?.copy(list = updatedList.orEmpty())
    }

    private fun deleteTodoItem(model: TodoModel?) {
        if (model == null) {
            return
        }

        val updatedList = uiState.value?.list?.filter { it.id != model.id }
        _uiState.value = uiState.value?.copy(list = updatedList.orEmpty())
    }

    fun processTodoItem(entryType: TodoContentType?, model: TodoModel?) {
        when (entryType) {
            TodoContentType.UPDATE -> model?.let { updateTodoItem(it) }
            TodoContentType.DELETE -> model?.let { deleteTodoItem(it) }
            else -> model?.let { addTodoItem(it) }
        }
    }
}