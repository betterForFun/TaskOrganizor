package com.example.taskorganizor.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.taskorganizor.data.model.TaskList
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskListDAO {
    @Insert
    suspend fun createList(taskList : TaskList)

    @Query("SELECT * FROM task_list")
    fun getAllList() : Flow<List<TaskList>>

    @Insert
    suspend fun updateTaskList(taskList: TaskList)

    @Delete
    suspend fun deleteTaskList(taskList: TaskList)
}