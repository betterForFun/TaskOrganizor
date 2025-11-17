package com.example.taskorganizor.data

import com.example.taskorganizor.data.database.TaskDAO
import com.example.taskorganizor.data.database.TaskListDAO
import com.example.taskorganizor.data.model.Task
import com.example.taskorganizor.data.model.TaskList
import kotlinx.coroutines.flow.Flow

class TaskRepository(val taskDAO : TaskDAO, val taskListDAO: TaskListDAO) {

    suspend fun createTask(task : Task){
        taskDAO.createTask(task)
    }

    fun getAllTasks() : Flow<List<Task>> {
        return taskDAO.getAllTasks()
    }

    fun getTasksFromList(listId : Int) : Flow<List<Task>> {
        return taskDAO.getTasksFromList(listId)
    }

    fun getStarredTasks() : Flow<List<Task>> {
        return taskDAO.getStarredTasks()
    }
    suspend fun updateTask(task : Task){
        taskDAO.updateTask(task)
    }

    suspend fun deleteTask(task : Task){
        taskDAO.deleteTask(task)
    }

    suspend fun createTaskList(name : String){
        val taskList = TaskList(name = name)
        taskListDAO.createList(taskList)
    }

    fun getTaskList() : Flow<List<TaskList>>{
        return taskListDAO.getAllList()
    }

    suspend fun updateTaskList(taskList: TaskList){
        taskListDAO.updateTaskList(taskList)
    }

    suspend fun removeTaskList(taskList: TaskList){
        taskListDAO.deleteTaskList(taskList)
    }
}