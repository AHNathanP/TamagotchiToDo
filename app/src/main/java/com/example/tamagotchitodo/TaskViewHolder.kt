package com.example.tamagotchitodo

import androidx.recyclerview.widget.RecyclerView
import com.example.tamagotchitodo.databinding.ListItemLayoutBinding

class TaskViewHolder(val binding: ListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
    private lateinit var currentTask: Task

    fun bindTask(task: Task) {
        currentTask = task
        binding.taskName.text = currentTask.taskName
        binding.taskDueDateDate.text = currentTask.dueBy
    }
}