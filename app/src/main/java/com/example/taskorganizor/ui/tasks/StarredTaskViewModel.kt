package com.example.taskorganizor.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskorganizor.TaskOrganizerApplication
import com.example.taskorganizor.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class StarredTaskViewModel : ViewModel() {

    val taskRepository = TaskOrganizerApplication.taskRepository

    fun fetchAllTasks() : Flow<List<Task>> {
        return taskRepository.getStarredTasks()
    }

    fun onTaskUpdate(task: Task, onUpdateComplete: ()-> Unit) {
        viewModelScope.launch {
            taskRepository.updateTask(task)
            onUpdateComplete
        }
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            taskRepository.deleteTask(task)
        }
    }
}