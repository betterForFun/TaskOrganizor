package com.example.taskorganizor

import android.app.Application
import com.example.taskorganizor.data.TaskRepository
import com.example.taskorganizor.data.database.AppDatabase

class TaskOrganizerApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        val db = AppDatabase.createDatabase(context = this)
        val taskDAO = db.getTaskDAO()
        val taskListDAO = db.getTaskListDAO()
        taskRepository = TaskRepository(taskDAO, taskListDAO)
    }

    companion object{
        lateinit var taskRepository: TaskRepository
    }
}