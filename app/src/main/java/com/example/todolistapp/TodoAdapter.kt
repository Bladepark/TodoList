package com.example.todolistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ItemTodoBinding

class TodoAdapter : ListAdapter<TodoData, TodoAdapter.ViewHolder>(diffUtil) {

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
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todo = getItem(position)
        holder.bind(todo)
    }

    inner class ViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todo: TodoData) {
            with(binding) {
                tvTodoTitle.text = todo.title
                tvTodoDescription.text = todo.description
                todoSwitch.isChecked = todo.isBookmarked

                todoSwitch.setOnCheckedChangeListener { _, isChecked ->
                    todo.isBookmarked = todoSwitch.isChecked.not()
                }
            }
        }
    }

}