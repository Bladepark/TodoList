package com.example.todolistapp.bookmark

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.todo.data.TodoModel
import com.example.todolistapp.databinding.ItemBookmarkBinding

class BookmarkAdapter: ListAdapter<TodoModel, BookmarkAdapter.ViewHolder>(diffUtil) {
    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
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
        fun bind(todo: TodoModel) {
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

