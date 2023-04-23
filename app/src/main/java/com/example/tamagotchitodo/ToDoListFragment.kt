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

class ToDoListFragment : Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference
        val tasks = mutableListOf(Task("Clean bedroom", "02/03"), Task("Buy groceries", "02/04"))

        setFragmentResultListener("REQUESTING_NAME_KEY") { nameKey: String, bundle: Bundle ->
            val newTask = bundle.getString("NAME_KEY")
            val task = Task(newTask!!, "02/05")
            tasks.add(task)
        }

        binding.addButton.setOnClickListener {
            val action = ToDoListFragmentDirections.actionToDoListFragmentToAddFragment()
            rootView.findNavController().navigate(action)
        }

        val myAdapter = TaskAdapter(tasks)
        binding.recyclerView.adapter = myAdapter

        return rootView
    }
}