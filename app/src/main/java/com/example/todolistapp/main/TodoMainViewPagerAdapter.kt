package com.example.todolistapp.main

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.todolistapp.R
import com.example.todolistapp.bookmark.BookmarkFragment
import com.example.todolistapp.todo.list.TodoFragment

class TodoMainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = listOf(
        TodoMainTab(TodoFragment.newInstance(), R.string.main_tab_todo_title),
        TodoMainTab(BookmarkFragment.newInstance(), R.string.main_tab_bookmark_title),
    )

    fun getFragment(position: Int): Fragment = fragments[position].fragment

    fun getTodoFragment(): Fragment? = fragments.find {
        it.fragment::class.java == TodoFragment::class.java
    }?.fragment

    fun getTitle(position: Int): Int = fragments[position].title

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }
}