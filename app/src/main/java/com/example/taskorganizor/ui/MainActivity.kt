package com.example.taskorganizor.ui

import android.R
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.taskorganizor.data.model.TaskList
import com.example.taskorganizor.databinding.ActivityMainBinding
import com.example.taskorganizor.databinding.AddNewTaskBinding
import com.example.taskorganizor.databinding.AddNewTaskListBinding
import com.example.taskorganizor.databinding.TabButtonBinding
import com.example.taskorganizor.ui.tasks.StarredTaskFragment
import com.example.taskorganizor.ui.tasks.TasksFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.sidesheet.SideSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    private var currentTaskLists : List<TaskList> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        lifecycleScope.launch {
            viewModel.getTaskLists().collectLatest { taskLists ->
                val viewPager = binding.viewPager
                currentTaskLists = taskLists
                viewPager.adapter = ViewPagerAdaptor(this@MainActivity, taskLists)
                viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

                TabLayoutMediator(binding.mainTabs, binding.viewPager) { tab, position ->
                    when (position) {
                        0 -> tab.icon = ContextCompat.getDrawable(this@MainActivity, R.drawable.btn_star)
                        taskLists.size + 1 -> {
                            val buttonBinding = TabButtonBinding.inflate(layoutInflater)
                            tab.customView = buttonBinding.root.apply {
                                setOnClickListener {
                                    showAddTaskListDialog()
                                }
                                setOnLongClickListener {
                                    viewModel.deleteTaskList(taskLists[position - 1])
                                    true
                                }
                            }
                        }
                        else -> tab.text = taskLists[position - 1].name
                    }
                }.attach()
            }

        }

        binding.addTask.setOnClickListener {
            showAddTask()
        }
    }

    private fun showAddTaskListDialog() {
        val addTaskListBinding = AddNewTaskListBinding.inflate(layoutInflater)
        MaterialAlertDialogBuilder(this)
            .setTitle("Add List")
            .setView(addTaskListBinding.root)
            .setPositiveButton("Save"){dialog, _ ->
                viewModel.createTaskList(addTaskListBinding.taskListName.text.toString())
                dialog.dismiss()
            }
            .setNegativeButton("Cancle"){dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    fun showAddTask() {
        val addTaskBinding = AddNewTaskBinding.inflate(layoutInflater)
        val dialog = BottomSheetDialog(this)
        val title = addTaskBinding.titleTextId
        val detail = addTaskBinding.descriptionTextId
        val detailField = addTaskBinding.descriptionTextId
        val star = addTaskBinding.starButton
        dialog.setContentView(addTaskBinding.root)
        dialog.show()
        addTaskBinding.saveNewTaskButton.isEnabled = false
        addTaskBinding.titleTextId.addTextChangedListener { input ->
            addTaskBinding.saveNewTaskButton.isEnabled = !input?.trim().isNullOrEmpty()
        }
        addTaskBinding.addDescriptionButton.setOnClickListener {
            detailField.visibility =
                if (detailField.visibility == View.VISIBLE) View.GONE else View.VISIBLE
        }
        addTaskBinding.starButton.setOnClickListener {

        }

        addTaskBinding.saveNewTaskButton.setOnClickListener {
            viewModel.createTask(
                title = title.text.toString(),
                description = detail.text.toString(),
                id = currentTaskLists[binding.viewPager.currentItem - 1].listId
            )
            dialog.dismiss()
        }

    }

    inner class ViewPagerAdaptor(fragmentActivity: FragmentActivity, val taskList : List<TaskList>) : FragmentStateAdapter(
        fragmentActivity
    ) {
        override fun createFragment(position: Int): Fragment {
            return when(position){
                0 -> StarredTaskFragment()
                taskList.size + 1 -> Fragment()
                else -> TasksFragment(taskList[position - 1].listId)
            }
        }
        override fun getItemCount(): Int {
            return taskList.size + 2
        }

    }
}