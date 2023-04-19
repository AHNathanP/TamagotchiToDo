package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentToDoListBinding

class ToDoListFragment : Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val rootView = binding.root

        val tasks = mutableListOf(Task("Clean bedroom", "I need to clean my room", "02/03"),
            Task("Buy groceries", "Be sure to get milk", "02/04"))
        tasks.add(Task("Do homework", "Also study for test", "02/03"))

        binding.addButton.setOnClickListener {
            val action = ToDoListFragmentDirections.actionToDoListFragmentToAddFragment()
            rootView.findNavController().navigate(action)
        }

        val myAdapter = TaskAdapter(tasks)
        binding.recyclerView.adapter = myAdapter

        return rootView
    }
}