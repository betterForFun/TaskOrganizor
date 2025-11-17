package com.example.taskorganizor.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Task_List")
data class TaskList (
    @PrimaryKey(autoGenerate = true) val listId : Int = 0,
    val name : String,
)