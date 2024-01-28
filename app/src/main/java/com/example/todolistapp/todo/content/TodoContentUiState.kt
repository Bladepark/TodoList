package com.example.todolistapp.todo.content

import com.example.todolistapp.todo.data.TodoModel

data class TodoContentUiState(
    val todoModel: TodoModel,
    val btnVisible: Boolean = false
)
