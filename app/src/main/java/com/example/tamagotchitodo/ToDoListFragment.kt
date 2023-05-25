package com.example.tamagotchitodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentToDoListBinding
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.*


class ToDoListFragment : Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference
    lateinit var taskList: MutableList<Task>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference

        taskList = mutableListOf()
        val myAdapter = TaskAdapter(taskList)
        binding.recyclerView.adapter = myAdapter


        binding.addButton.setOnClickListener {
            val action = ToDoListFragmentDirections.actionToDoListFragmentToAddFragment()
            rootView.findNavController().navigate(action)
        }
        binding.backButton.setOnClickListener {
            rootView.findNavController().navigateUp()
        }


        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                val allDBEntries = snapshot.children
                var numOfTasksAdded = 0

                for (allTaskEntries in allDBEntries) {
                    for (singleTaskEntry in allTaskEntries.children) {
                        if (singleTaskEntry.child("monthDue").getValue() != null
                            && singleTaskEntry.child("dayDue").getValue() != null) {
                            numOfTasksAdded++
                            val taskName = singleTaskEntry.child("taskName").getValue().toString()
                            val taskMonthDue = Integer.parseInt(singleTaskEntry.child("monthDue").getValue().toString())
                            val taskDayDue = Integer.parseInt(singleTaskEntry.child("dayDue").getValue().toString())
                            val key = singleTaskEntry.key.toString()
                            val currentTask = Task(taskName, taskMonthDue, taskDayDue, key)
                            taskList.add(currentTask)
                            myAdapter.notifyDataSetChanged()
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("ToDoListFragment", "Failed to read value.", error.toException())
            }
        })

        return rootView
    }
}