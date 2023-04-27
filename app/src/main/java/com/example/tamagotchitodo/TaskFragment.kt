package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val args = TaskFragmentArgs.fromBundle(requireArguments())
        binding.taskName.text = args.taskNameArg
        binding.taskDueByDate.text = args.taskDueDateArg

        binding.deleteButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_message)
                .setPositiveButton(R.string.alert_positive) { dialog, which ->
                    val index = viewModel.getTaskKey()
                    viewModel.deleteTaskWithoutUpdate(index)
                    rootView.findNavController().navigateUp()
                }
                .setNegativeButton(R.string.alert_negative) { dialog, which ->
                    rootView.findNavController().navigateUp()
                }
                .show()
        }
        binding.doneButton.setOnClickListener {
            val index = viewModel.getTaskKey()
            viewModel.deleteTask(index)
            rootView.findNavController().navigateUp()
        }

        return rootView
    }
}