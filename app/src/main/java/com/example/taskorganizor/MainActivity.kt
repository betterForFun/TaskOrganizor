package com.example.taskorganizor

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.taskorganizor.databinding.ActivityMainBinding
import com.example.taskorganizor.databinding.AddNewTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        val addTaskBinding = AddNewTaskBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        val viewPager = binding.viewPager
        viewPager.adapter = ViewPagerAdaptor(this)
        viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        TabLayoutMediator(binding.mainTabs, binding.viewPager) { tab, position ->
            tab.text = "Tasks"
        }.attach()

        binding.addTask.setOnClickListener {
            MaterialAlertDialogBuilder(this).setTitle("Add New Task")
                .setView(addTaskBinding.root)
                .setPositiveButton("save") { _, _ ->
                    Toast.makeText(this, "Please add task" , Toast.LENGTH_LONG).show()
                }
                .setNegativeButton("cancel", null)
                .show()
        }
    }

    inner class ViewPagerAdaptor(fragmentActivity: FragmentActivity) : FragmentStateAdapter(
        fragmentActivity
    ) {
        private val fragments = listOf(TasksFragment())
        override fun createFragment(position: Int): Fragment {
            return fragments[position]
        }

        override fun getItemCount(): Int {
            return fragments.size
        }

    }
}