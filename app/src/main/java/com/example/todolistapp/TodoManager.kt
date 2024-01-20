package com.example.todolistapp

object TodoManager {
    private val todoList: MutableList<TodoData> = mutableListOf()

    init {
        todoList.add(TodoData("title 0", "description 0", false))
        todoList.add(TodoData("title 1", "description 1", false))
        todoList.add(TodoData("title 2", "description 2", false))
    }

    fun addTodoItem(item : TodoData) {
        todoList.add(item)
    }

    fun getBookmarkedList() : List<TodoData> {
        return todoList.filter { it.isBookmarked }.toList()
    }

    fun getTodoList() : List<TodoData> {
        return todoList.toList()
    }

}