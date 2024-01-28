package com.example.todolistapp.todo.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todolistapp.databinding.ItemTodoBinding
import com.example.todolistapp.todo.data.TodoModel

class TodoAdapter : ListAdapter<TodoModel, TodoAdapter.ViewHolder>(diffUtil) {

    companion object {
        private val diffUtil = object : DiffUtil.ItemCallback<TodoModel>() {
            override fun areItemsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TodoModel, newItem: TodoModel): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface ItemClick {
        fun onClick(view : View, position : Int, item: TodoModel)
    }

    interface SwitchClick {
        fun onClick(view : View, position : Int, item: TodoModel, isChecked: Boolean)
    }

    var itemClick: ItemClick? = null
    var switchClick: SwitchClick? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position, item)
        }
    }

    inner class ViewHolder(private val binding: ItemTodoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: TodoModel) {
            with(binding) {
                tvTodoTitle.text = item.title
                tvTodoDescription.text = item.description
                todoSwitch.isChecked = item.isBookmarked

                todoSwitch.setOnCheckedChangeListener { buttonView, isChecked ->
                    switchClick?.onClick(buttonView, adapterPosition, item, isChecked)
                }
            }
        }
    }
}