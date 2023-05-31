package com.example.tamagotchitodo

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tamagotchitodo.databinding.ListItemLayoutBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TaskAdapter(val taskList: MutableList<Task>, val petKey: String, var tasksDone: Int): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val binding: ListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentTask: Task

        init {
            binding.root.setOnClickListener { view ->
                val taskName = currentTask.taskName
                val taskDueMonth = currentTask.monthDue
                val taskDueDay = currentTask.dayDue
                val taskDueDate = "$taskDueMonth/$taskDueDay"
                val taskKey = currentTask.key
                val action = ToDoListFragmentDirections
                    .actionToDoListFragmentToTaskFragment(taskName, taskDueDate,
                        taskKey, petKey, tasksDone)
                binding.root.findNavController().navigate(action)
            }
        }

        fun bindTask(task: Task) {
            currentTask = task
            binding.taskName.text = currentTask.taskName
            val taskDueDate = "${task.monthDue}/${task.dayDue}"
            binding.taskDueDateDate.text = taskDueDate
        }
    }
    override fun getItemCount(): Int {
        return taskList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ListItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val currentTask = taskList[position]
        holder.bindTask(currentTask)
    }
}