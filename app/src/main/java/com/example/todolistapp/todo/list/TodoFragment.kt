package com.example.todolistapp.todo.list

import android.app.Activity.RESULT_OK
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolistapp.databinding.FragmentTodoBinding
import com.example.todolistapp.todo.data.TodoContentType
import com.example.todolistapp.todo.content.TodoContentActivity
import com.example.todolistapp.todo.data.TodoModel


class TodoFragment : Fragment() {

    companion object {
        fun newInstance() = TodoFragment()
    }

    private var _binding: FragmentTodoBinding? = null
    private val binding get() = _binding!!

    private var _todoAdapter: TodoAdapter? = null
    private val todoAdapter get() = _todoAdapter

    private val viewModel: TodoViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
        initViewModel()
    }

    private fun initView() {
        setTodoAdapter()
    }

    private fun initViewModel() = with(viewModel) {
        uiState.observe(viewLifecycleOwner) {
            val test = it.copy()
            todoAdapter?.submitList(test.list)
        }
    }

    private fun setTodoAdapter() {
        _todoAdapter = TodoAdapter()
        binding.todoRecyclerView.apply {
            adapter = todoAdapter.also {
                it?.itemClick = object : TodoAdapter.ItemClick {
                    override fun onClick(view: View, position: Int, item: TodoModel) {
                        val intent = TodoContentActivity.newIntentForUpdate(requireContext(), item)
                        updateTodoLauncher.launch(intent)
                    }
                }
                it?.switchClick = object : TodoAdapter.SwitchClick {
                    override fun onClick(view: View, position: Int, item: TodoModel, isChecked: Boolean) {
                        viewModel.updateBookmarkStatus(item)
                    }
                }
            }
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private val updateTodoLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {result ->
            if (result.resultCode == RESULT_OK) {
                val entryType = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_ENTRY_TYPE,
                        TodoContentType::class.java
                    )
                } else {
                    result.data?.getParcelableExtra(
                        TodoContentActivity.EXTRA_TODO_ENTRY_TYPE
                    )
                }
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

                processTodoItem(entryType, todoModel)
            }
        }

    fun processTodoItem(entryType: TodoContentType?, todoModel: TodoModel?) {
        viewModel.processTodoItem(entryType, todoModel)
    }

    override fun onDestroyView() {
        _binding = null
        _todoAdapter = null
        super.onDestroyView()
    }
}