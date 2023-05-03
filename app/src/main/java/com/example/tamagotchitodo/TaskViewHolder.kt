package com.example.tamagotchitodo

import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tamagotchitodo.databinding.ListItemLayoutBinding

//class TaskViewHolder(val binding: ListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
//    private lateinit var currentTask: Task
//
//    init {
//        binding.root.setOnClickListener { view ->
//            val taskName = currentTask.taskName
//            val taskDueDate = currentTask.dueBy
//            val action = ToDoListFragmentDirections
//                .actionToDoListFragmentToTaskFragment(taskName, taskDueDate)
//            binding.root.findNavController().navigate(action)
//        }
//    }
//
//    fun bindTask(task: Task) {
//        currentTask = task
//        binding.taskName.text = currentTask.taskName
//        binding.taskDueDateDate.text = currentTask.dueBy
//    }
//}