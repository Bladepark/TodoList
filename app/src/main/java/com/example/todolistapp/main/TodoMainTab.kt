package com.example.todolistapp.main

import androidx.annotation.StringRes
import androidx.fragment.app.Fragment

data class TodoMainTab(
    val fragment: Fragment,
    @StringRes val title: Int
)
