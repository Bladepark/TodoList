package com.example.todolistapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.OnBackPressedCallback
import com.example.todolistapp.databinding.ActivityAddTodoBinding

class AddTodoActivity : AppCompatActivity() {

    private val binding by lazy { ActivityAddTodoBinding.inflate(layoutInflater) }
    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            exit()
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        onAddButtonPressed()
        onBackButtonPressed()
        this.onBackPressedDispatcher.addCallback(this, callback)
    }

    private fun onAddButtonPressed() {
        binding.btnAddTodo.setOnClickListener {
            val item = TodoData(
                binding.etTitle.text.toString(),
                binding.etDescription.text.toString(),
                false
            )
            TodoManager.addTodoItem(item)
            setResult(RESULT_OK, intent)
            exit()
        }
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