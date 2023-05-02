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

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.addButtonAddFrag.setOnClickListener {
            val taskName = binding.newTaskName.text.toString()
            val checkOne = binding.newTaskMonth.text.toString()
            val checkTwo = binding.newTaskDay.text.toString()
            if (checkOne=="" || checkTwo=="") {
                Toast.makeText(requireContext(), R.string.toast_message, Toast.LENGTH_SHORT).show()
            }
            else {
                val dueDateMonth = binding.newTaskMonth.text.toString().toInt()
                val dueDateDay = binding.newTaskDay.text.toString().toInt()
                if (checkDate(dueDateMonth, dueDateDay)) {
                    val dueDate = "$dueDateMonth/$dueDateDay"
                    setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to taskName))
                    setFragmentResult("REQUESTING_TIME_KEY", bundleOf("TIME_KEY" to dueDate))
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