package com.example.todolistapp.todo.list

import com.example.todolistapp.todo.data.TodoModel

data class TodoUiState(
    val list: List<TodoModel>
) {
    companion object {

        fun init() = TodoUiState(
            list = emptyList()
        )
    }
}
