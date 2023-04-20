package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.tamagotchitodo.databinding.FragmentTaskBinding

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = TaskFragmentArgs.fromBundle(requireArguments())
        binding.taskName.text = args.taskNameArg
        binding.taskDueByDate.text = args.taskDueDateArg

        return rootView
    }
}