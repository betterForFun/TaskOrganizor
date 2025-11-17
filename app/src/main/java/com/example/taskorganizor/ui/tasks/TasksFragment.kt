package com.example.taskorganizor.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.taskorganizor.data.model.Task
import com.example.taskorganizor.databinding.TaskFragmentBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TasksFragment(val taskListId : Int) : Fragment(), TaskAdaptor.TaskUpdateListener{
    private val viewModel : TaskViewModel by viewModels()

    private lateinit var binding: TaskFragmentBinding
    private val taskAdaptor = TaskAdaptor(this)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = TaskFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tasksRecyclerView.adapter = taskAdaptor
        fetchAllTasks()
    }

    fun fetchAllTasks(){
        lifecycleScope.launch {
            viewModel.fetchAllTasksFromList(taskListId).collectLatest { tasks ->
                taskAdaptor.updateTasks(tasks)
            }
        }
    }

    override fun onTaskUpdate(task: Task) {
        viewModel.onTaskUpdate(task){
        }
    }

    override fun deleteTask(task: Task) {
        viewModel.deleteTask(task)
    }
}