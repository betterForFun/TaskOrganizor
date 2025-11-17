package com.example.taskorganizor.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.taskorganizor.data.model.Task
import com.example.taskorganizor.data.model.TaskList

@Database(entities = [Task::class, TaskList::class], version = 4)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getTaskDAO(): TaskDAO

    abstract fun getTaskListDAO(): TaskListDAO

    companion object {
        @Volatile
        private var DATABASE_INSTANCE: AppDatabase? = null

        private val MIGRATE_2_TO_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """CRATE TABLE IF NOT EXITS 'Task_List' (
                        'listId' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        'name' TEXT NOT NULL
                    ) """.trimMargin()
                )
            }
        }

        fun createDatabase(context: Context): AppDatabase {
            return DATABASE_INSTANCE ?: synchronized(this) {
                DATABASE_INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,              // use appContext
                    AppDatabase::class.java,
                    "myDataBase"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { DATABASE_INSTANCE = it }
            }
        }
    }

}