package com.example.todolistapp.main

import android.app.Activity
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.viewpager2.widget.ViewPager2
import com.example.todolistapp.todo.content.TodoContentActivity
import com.example.todolistapp.todo.list.TodoFragment
import com.example.todolistapp.todo.data.TodoModel
import com.example.todolistapp.databinding.ActivityMainBinding
import com.example.todolistapp.todo.data.TodoContentType
import com.google.android.material.tabs.TabLayoutMediator

class TodoMainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val viewPagerAdapter by lazy { TodoMainViewPagerAdapter(this) }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initView()
    }

    private fun initView() {
        initViewPager()
        onAddTodoFabPressed()
    }

    private fun initViewPager() {
        //Adapter 연결
        binding.mainViewPager.apply {
            adapter = viewPagerAdapter
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    if (viewPagerAdapter.getFragment(position) is TodoFragment) {
                        binding.addTodoFab.show()
                    } else {
                        binding.addTodoFab.hide()
                    }
                }
            })
        }

        //ViewPager, TabLayout 연결
        TabLayoutMediator(binding.mainNavigationView, binding.mainViewPager) { tab, position ->
            tab.setText(viewPagerAdapter.getTitle(position))
        }.attach()
    }

    private fun onAddTodoFabPressed() {
        binding.addTodoFab.setOnClickListener {
            createTodoLauncher.launch(TodoContentActivity.newIntentForCreate(this@TodoMainActivity))
        }
    }

    private val createTodoLauncher =
        registerForActivityResult(  ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val todoModel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL,
                        TodoModel::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_MODEL
                    )
                }

                val fragment = viewPagerAdapter.getTodoFragment() as? TodoFragment
                fragment?.processTodoItem(TodoContentType.CREATE, todoModel)
            }
        }
}