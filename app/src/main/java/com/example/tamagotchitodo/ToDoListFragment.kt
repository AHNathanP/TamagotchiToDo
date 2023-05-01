package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentToDoListBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.fragment.app.activityViewModels

class ToDoListFragment : Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference
    private val viewModel: StatusViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference
        var newTaskName = ""
        var newTaskDueDate = ""

        setFragmentResultListener("REQUESTING_NAME_KEY") { nameKey: String, bundle: Bundle ->
            newTaskName = bundle.getString("NAME_KEY")?:""
        }
        setFragmentResultListener("REQUESTING_TIME_KEY") { timeKey: String, bundle: Bundle ->
            newTaskDueDate = bundle.getString("TIME_KEY")?:""
            val task = Task(newTaskName, newTaskDueDate)
            viewModel.addTask(task)
        }

        binding.addButton.setOnClickListener {
            val action = ToDoListFragmentDirections.actionToDoListFragmentToAddFragment()
            rootView.findNavController().navigate(action)
        }
        binding.backButton.setOnClickListener {
            rootView.findNavController().navigateUp()
        }

        val taskList = viewModel.listOfTasks.value?: mutableListOf()
        val myAdapter = TaskAdapter(taskList, viewModel)
        binding.recyclerView.adapter = myAdapter

        return rootView
    }
}