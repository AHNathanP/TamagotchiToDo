package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.example.tamagotchitodo.databinding.FragmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference
    private val viewModel: StatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root

        dbRef = Firebase.database.reference

        binding.addButtonAddFrag.setOnClickListener {
            val checkMonth = binding.newTaskMonth.text.toString()
            val checkDay = binding.newTaskDay.text.toString()
            if ((checkMonth=="" || checkDay=="") || !(checkDate(checkMonth.toInt(), checkDay.toInt()))) {
                Toast.makeText(requireContext(), R.string.toast_message, Toast.LENGTH_SHORT).show()
            }
            else {
                val taskName = binding.newTaskName.text.toString()
                val dueDateMonth = Integer.parseInt(binding.newTaskMonth.text.toString())
                val dueDateDay = Integer.parseInt(binding.newTaskDay.text.toString())
                val task = Task(taskName, dueDateMonth, dueDateDay)
                dbRef.child("tasks").push().setValue(task)
                Toast.makeText(requireContext(), R.string.toast_task_added, Toast.LENGTH_SHORT).show()
                viewModel.listOfTasks.value?.add(task)
            }
        }
        return rootView
    }
    fun checkDate(month: Int, day: Int): Boolean {
        if (month !in 1..12) {
            return false
        }
        return if ((month ==4 || month ==6 || month ==9 || month==11) && day !in 1..30) {
            false
        } else if (month==2 && day !in 1..29) {
            false
        } else day in 1..31
    }
}