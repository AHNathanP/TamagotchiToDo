package com.example.tamagotchitodo

import android.util.Property
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tamagotchitodo.databinding.ListItemLayoutBinding

class TaskAdapter(val taskList: MutableList<Task>): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val binding: ListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentTask: Task

        init {
            binding.root.setOnClickListener { view ->
                val taskName = currentTask.taskName
                val taskDueDate = currentTask.dueBy
                val action = ToDoListFragmentDirections
                    .actionToDoListFragmentToTaskFragment(taskName, taskDueDate)
                binding.root.findNavController().navigate(action)
            }
            binding.checkbox.setOnClickListener {
                deleteItem(this.position)
            }
        }

        fun bindTask(task: Task) {
            currentTask = task
            binding.taskName.text = currentTask.taskName
            binding.taskDueDateDate.text = currentTask.dueBy
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
    fun deleteItem(index: Int) {
        taskList.removeAt(index)
        notifyItemRemoved(index)
    }
}