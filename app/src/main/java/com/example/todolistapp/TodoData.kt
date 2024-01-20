package com.example.todolistapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TodoData(
    var title : String,
    var description : String,
    var isBookmarked : Boolean
) : Parcelable
