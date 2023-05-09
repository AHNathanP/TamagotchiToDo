package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root

        dbRef = Firebase.database.reference

        binding.addButtonAddFrag.setOnClickListener {
            val checkMonth = binding.newTaskMonth.text.toString()
            val checkDay = binding.newTaskDay.text.toString()
            if (checkMonth=="" || checkDay=="") {
                Toast.makeText(requireContext(), R.string.toast_message, Toast.LENGTH_SHORT).show()
            }
            else {
                val taskName = binding.newTaskName.text.toString()
                val dueDateMonth = checkMonth.toInt()
                val dueDateDay = checkDay.toInt()
                if (checkDate(dueDateMonth, dueDateDay)) {
//                    setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to taskName))      non-firebase stuff
//                    setFragmentResult("REQUESTING_MONTH_KEY", bundleOf("MONTH_KEY" to dueDateMonth))
//                    setFragmentResult("REQUESTING_DAY_KEY", bundleOf("DAY_KEY" to dueDateDay))
                    val task = Task(taskName, dueDateMonth, dueDateDay)
                    dbRef.child("tasks").push().setValue(task)
                    rootView.findNavController().navigateUp()
                }
                else {
                    Toast.makeText(requireContext(), R.string.toast_message, Toast.LENGTH_SHORT).show()
                }
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