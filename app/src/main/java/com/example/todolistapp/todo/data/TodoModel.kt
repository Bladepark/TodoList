package com.example.todolistapp.todo.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.UUID

@Parcelize
data class TodoModel(
    val id : String = UUID.randomUUID().toString(),
    var title : String? = null,
    var description : String? = null,
    var isBookmarked : Boolean = false
) : Parcelable
