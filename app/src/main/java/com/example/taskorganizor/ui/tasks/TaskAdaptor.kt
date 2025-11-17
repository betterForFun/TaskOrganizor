package com.example.taskorganizor.ui.tasks

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.taskorganizor.data.model.Task
import com.example.taskorganizor.databinding.ItemTaskBinding

class TaskAdaptor(val listener: TaskUpdateListener) :
    RecyclerView.Adapter<TaskAdaptor.ViewHolder>() {

    private var tasks : List<Task> = listOf()
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        return ViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun updateTasks(tasks : List<Task>){
        this.tasks = tasks
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        holder.bind(tasks[position])
    }

    override fun getItemCount(): Int {
        return tasks.size
    }


    inner class ViewHolder(val binding: ItemTaskBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(task: Task) {
            binding.root.setOnLongClickListener {
                listener.deleteTask(task)
                Toast.makeText(binding.root.context, "Task Delted", Toast.LENGTH_SHORT).show()
                true
            }
            binding.taskCheckBox.isChecked = task.isCompleted
            binding.taskFavoriteBox.isChecked = task.isStarred
            binding.taskTitle.text = task.title
            if(task.description.isNullOrEmpty()){
                binding.taskDetails.visibility = View.GONE
            }else{
                binding.taskDetails.text = task.description
            }
            if (task.isCompleted) {
                binding.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.taskDetails.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            }else{
                binding.taskTitle.paintFlags = 0
                binding.taskDetails.paintFlags = 0
            }
            binding.taskCheckBox.setOnClickListener {
                var updatedTask : Task? = null
                updatedTask = if(binding.taskCheckBox.isChecked){
                    task.copy(isCompleted = true)
                }else{
                    task.copy(isCompleted = false)
                }

                listener.onTaskUpdate(updatedTask)
            }
            binding.taskFavoriteBox.setOnClickListener {
                var updatedTask : Task? = null
                updatedTask = if(binding.taskFavoriteBox.isChecked){
                    task.copy(isStarred = true)
                }else{
                    task.copy(isStarred = false)
                }
                listener.onTaskUpdate(updatedTask)
            }
        }
    }

    interface TaskUpdateListener {
        fun onTaskUpdate(task: Task)

        fun deleteTask(task : Task)
    }
}