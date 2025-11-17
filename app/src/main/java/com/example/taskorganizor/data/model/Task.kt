package com.example.taskorganizor.data.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "myTask",
    foreignKeys = [
        ForeignKey(entity = TaskList::class,
            parentColumns = ["listId"],
            childColumns = ["belongsToListId"],
            onDelete = ForeignKey.CASCADE
            )
    ]
)
data class Task(
    @PrimaryKey(autoGenerate = true) val taskId : Int = 0,
    val title : String,
    val description : String? = null,
    val isStarred : Boolean = false,
    val isCompleted : Boolean = false,
    val belongsToListId : Int
)