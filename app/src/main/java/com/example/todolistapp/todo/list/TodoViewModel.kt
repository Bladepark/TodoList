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


    fun updateBookmarkStatus(model: TodoModel?) {
        if (model == null) {
            return
        }
        _uiState.value = uiState.value?.copy(list = uiState.value?.list?.map {
            if (it.id == model.id) it.copy(isBookmarked = !it.isBookmarked)
            else it
        }.orEmpty())
    }

    private fun addTodoItem(model: TodoModel?) {
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
        model.let { updatedModel ->
            _uiState.value =
                uiState.value?.copy(list = uiState.value?.list?.map { if (it.id == updatedModel.id) updatedModel else it }
                    .orEmpty())
        }
    }

    private fun deleteTodoItem(model: TodoModel?) {
        if (model == null) {
            return
        }
        model.let { deletedModel ->
            _uiState.value =
                uiState.value?.copy(list = uiState.value?.list?.filter { it.id != deletedModel.id }
                    .orEmpty())
        }
    }

    fun processTodoItem(entryType: TodoContentType?, model: TodoModel?) {
        when (entryType) {
            TodoContentType.UPDATE -> model?.let { updateTodoItem(it) }
            TodoContentType.DELETE -> model?.let { deleteTodoItem(it) }
            else -> model?.let { addTodoItem(it) }
        }
    }
}