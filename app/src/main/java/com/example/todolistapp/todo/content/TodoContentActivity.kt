package com.example.todolistapp.todo.content

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.example.todolistapp.R
import com.example.todolistapp.databinding.ActivityTodoContentBinding
import com.example.todolistapp.todo.data.TodoContentType
import com.example.todolistapp.todo.data.TodoModel

class TodoContentActivity : AppCompatActivity() {

    private val binding by lazy { ActivityTodoContentBinding.inflate(layoutInflater) }
    private val viewModel: TodoContentViewModel by viewModels {
        TodoContentViewModelFactory(
            entryType,
            todoModel
        )
    }
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exit()
        }
    }

    companion object {
        const val EXTRA_TODO_MODEL = "extra_todo_model"
        const val EXTRA_TODO_ENTRY_TYPE = "extra_todo_entry_type"

        fun newIntentForCreate(
            context: Context
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.CREATE.ordinal)
        }

        fun newIntentForUpdate(
            context: Context,
            todoModel: TodoModel
        ) = Intent(context, TodoContentActivity::class.java).apply {
            putExtra(EXTRA_TODO_ENTRY_TYPE, TodoContentType.UPDATE.ordinal)
            putExtra(EXTRA_TODO_MODEL, todoModel)
        }
    }

    private val todoModel: TodoModel? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra(EXTRA_TODO_MODEL, TodoModel::class.java)
        } else {
            intent.getParcelableExtra(EXTRA_TODO_MODEL)
        }
    }

    private val entryType: TodoContentType by lazy {
        TodoContentType.getEntryType(
            intent.getIntExtra(
                EXTRA_TODO_ENTRY_TYPE,
                TodoContentType.CREATE.ordinal
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
        initViewModel()
    }

    private fun initView() {
        onAddOrUpdateButtonPressed()
        onDeleteButtonPressed()
        onBackButtonPressed()
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun initViewModel() {
        viewModel.contentUiState.observe(this@TodoContentActivity) { state ->
            binding.etTitle.setText(state.todoModel.title)
            binding.etDescription.setText(state.todoModel.description)
            binding.btnDeleteTodo.isVisible = state.btnVisible
            binding.btnAddOrUpdateTodo.setText(R.string.todo_content_update)
        }
    }

    private fun onAddOrUpdateButtonPressed() = with(binding) {
        btnAddOrUpdateTodo.setOnClickListener {
            val title = etTitle.text.toString()
            val description = etDescription.text.toString()
            when (entryType) {
                TodoContentType.UPDATE -> {
                    val updatedTodoModel = todoModel?.copy(title = title, description = description)
                    setResultData(updatedTodoModel, TodoContentType.UPDATE)
                }
                else -> {
                    val newTodoModel = TodoModel(title = title, description = description, isBookmarked = false)
                    setResultData(newTodoModel, TodoContentType.CREATE)
                }
            }
        }
    }

    private fun onDeleteButtonPressed() = with(binding) {
        btnDeleteTodo.setOnClickListener {
            val deleteTodoModel = todoModel
            setResultData(deleteTodoModel, TodoContentType.DELETE)
        }
    }

    private fun setResultData(todoModel: TodoModel?, entryType: TodoContentType) {
        val intent = Intent().apply {
            putExtra(EXTRA_TODO_MODEL, todoModel)
            putExtra(EXTRA_TODO_ENTRY_TYPE, entryType)
        }
        setResult(Activity.RESULT_OK, intent)
        exit()
    }

    private fun onBackButtonPressed() {
        binding.btnBack.setOnClickListener {
            exit()
        }
    }

    private fun exit() {
        if (isFinishing.not()) finish()
    }
}