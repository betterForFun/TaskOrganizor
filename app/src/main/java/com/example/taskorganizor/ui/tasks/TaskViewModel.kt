package com.example.taskorganizor.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskorganizor.TaskOrganizerApplication
import com.example.taskorganizor.data.model.Task
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class TaskViewModel : ViewModel() {

    val taskRepository = TaskOrganizerApplication.taskRepository

    fun fetchAllTasks(): Flow<List<Task>> {
        return taskRepository.getAllTasks()
    }

    fun fetchAllTasksFromList(listId: Int): Flow<List<Task>> {
        return taskRepository.getTasksFromList(listId)
    }

    fun onTaskUpdate(task: Task, onUpdateComplete: () -> Unit) {
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