package com.example.tamagotchitodo

import android.os.Bundle
import android.util.Log
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
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class ToDoListFragment : Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference
    //lateinit var taskList: MutableList<Task>
    private val viewModel: StatusViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference
        //taskList = viewModel.listOfTasks.value?: mutableListOf()

        var newTaskName = ""
        var newTaskDueMonth = 0
        var newTaskDueDay = 0

        setFragmentResultListener("REQUESTING_NAME_KEY") { nameKey: String, bundle: Bundle ->
            newTaskName = bundle.getString("NAME_KEY")?:""
        }
        setFragmentResultListener("REQUESTING_MONTH_KEY") { monthKey: String, bundle: Bundle ->
            newTaskDueMonth = bundle.getInt("MONTH_KEY")?:0
        }
        setFragmentResultListener("REQUESTING_DAY_KEY") { dayKey: String, bundle: Bundle ->
            newTaskDueDay = bundle.getInt("DAY_KEY")?:0
            val newTask = Task(newTaskName, newTaskDueMonth, newTaskDueDay)
            viewModel.addTask(newTask)
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

//        dbRef.addValueEventListener(object: ValueEventListener {
//            override fun onDataChange(snapshot: DataSnapshot) {
//                val allDBEntries = snapshot.children
//
//                var numOfTasksAdded = 0
//
//                for (allTaskEntries in allDBEntries) {
//                    for (singleTaskEntry in allTaskEntries.children) {
//                        numOfTasksAdded++
//                        val taskName = singleTaskEntry.child("name").getValue()//.toString()
//                        val taskDueDate = singleTaskEntry.child("dueDate").getValue()//.toString()
//                        if (!(taskName == null || taskDueDate == null)) {
//                            val name = singleTaskEntry.child("name").getValue().toString()
//                            val dueDate = singleTaskEntry.child("name").getValue().toString()
//                            val currentTask = Task(name, dueDate)
//                            taskList.add(currentTask)
//                            viewModel.addTask(currentTask)
//                            myAdapter.notifyDataSetChanged()
//                        }
//                        //val currentTask = Task(taskName, taskDueDate)
//                        //taskList.add(currentTask)
//
//                    }
//                }
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                Log.w("ToDoListFragment", "Failed to read value.", error.toException())
//            }
//        })

        return rootView
    }
}