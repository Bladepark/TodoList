package com.example.todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ItemBookmarkBinding

class BookmarkAdapter: ListAdapter<TodoData, BookmarkAdapter.ViewHolder>(diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TodoData>() {
            override fun areItemsTheSame(oldItem: TodoData, newItem: TodoData): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TodoData, newItem: TodoData): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemBookmarkBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    inner class ViewHolder(private val binding: ItemBookmarkBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoData) {
            with(binding) {
                tvBookmarkTitle.text = todo.title
                tvBookmarkDescription.text = todo.description
                bookmarkSwitch.isChecked = todo.isBookmarked

                bookmarkSwitch.setOnCheckedChangeListener { _, isChecked ->
                    todo.isBookmarked = bookmarkSwitch.isChecked.not()
                }
            }
        }
    }
}

