package com.example.tamagotchitodo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.tamagotchitodo.databinding.ListItemLayoutBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TaskAdapter(val taskList: MutableList<Task>, val viewModel: StatusViewModel): RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {
    inner class TaskViewHolder(val binding: ListItemLayoutBinding): RecyclerView.ViewHolder(binding.root) {
        private lateinit var currentTask: Task
        lateinit var dbRef : DatabaseReference

        init {
            binding.root.setOnClickListener { view ->
                viewModel.setTaskKey(this.position)
                val taskName = currentTask.taskName
                val taskDueMonth = currentTask.monthDue
                val taskDueDay = currentTask.dayDue
                val taskDueDate = "$taskDueMonth/$taskDueDay"
                val taskKey = currentTask.key
                val action = ToDoListFragmentDirections
                    .actionToDoListFragmentToTaskFragment(taskName, taskDueDate, taskKey)
                binding.root.findNavController().navigate(action)
            }
            binding.checkbox.setOnClickListener {
                dbRef = Firebase.database.reference
                val key = currentTask.key
                dbRef.child("tasks").child(key).removeValue()
                viewModel.deleteTask(this.position)
                Snackbar.make(binding.checkbox,
                    R.string.snackbar_message, Snackbar.LENGTH_SHORT).show()
//                notifyItemRemoved(this.position)
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