package com.example.taskorganizor.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskorganizor.TaskOrganizerApplication
import com.example.taskorganizor.data.TaskRepository
import com.example.taskorganizor.data.model.Task
import com.example.taskorganizor.data.model.TaskList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    val taskRepository : TaskRepository = TaskOrganizerApplication.taskRepository

    fun getTaskLists() : Flow<List<TaskList>> {
        return taskRepository.getTaskList()
    }
    fun createTask(title : String, description: String?, id : Int){
        viewModelScope.launch {
            val task = Task(title = title, description = description, belongsToListId = id)
            taskRepository.createTask(task)
        }
    }

    fun createTaskList(name : String){
        viewModelScope.launch {
            taskRepository.createTaskList(name)
        }
    }

    fun deleteTaskList(taskList: TaskList){
        viewModelScope.launch {
            taskRepository.removeTaskList(taskList)
        }
    }

}