package com.example.taskorganizor.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.taskorganizor.data.model.Task
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDAO {

    @Insert
    suspend fun createTask(task : Task)

    @Query("SELECT * FROM myTask")
    fun getAllTasks() : Flow<List<Task>>

    @Query("SELECT * FROM myTask WHERE isStarred == 1")
    fun getStarredTasks() : Flow<List<Task>>

    @Query("SELECT * FROM myTask WHERE belongsToListId == :listId")
    fun getTasksFromList(listId : Int) : Flow<List<Task>>

    @Update
    suspend fun updateTask(task : Task)

    @Delete
    suspend fun deleteTask(task : Task)

    // delete all rows
    @Query("DELETE FROM myTask")
    suspend fun deleteAll()
}