package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatusViewModel by activityViewModels()
    lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference
        val args = TaskFragmentArgs.fromBundle(requireArguments())
        binding.taskName.text = args.taskNameArg
        binding.taskDueByDate.text = args.taskDueDateArg
        val taskKey = args.keyArg
        val petKey = args.petKeyArg
        var doneOrDeleted = false

        binding.deleteButton.setOnClickListener {
            if (!doneOrDeleted) {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle(R.string.alert_title)
                    .setMessage(R.string.alert_message)
                    .setPositiveButton(R.string.alert_positive) { dialog, which ->
                        dbRef.child("tasks").child(taskKey).removeValue()
                        Toast.makeText(requireContext(), R.string.toast_task_deleted, Toast.LENGTH_SHORT).show()
                        doneOrDeleted = true
                        viewModel.removeTaskWithName(dbRef.child("tasks").child(taskKey).child("taskName").toString())
                    }
                    .setNegativeButton(R.string.alert_negative) { dialog, which ->
                        rootView.findNavController().navigateUp()
                    }
                    .show()
            }
        }
        binding.doneButton.setOnClickListener {
            if (!doneOrDeleted) {
                dbRef.child("tasks").child(taskKey).removeValue()
                dbRef.child("pets").child(petKey).child("numOfTasksDone").setValue(1)
                Snackbar.make(binding.doneButton, R.string.snackbar_message, Snackbar.LENGTH_SHORT).show()
                doneOrDeleted = true
                viewModel.removeTaskWithName(dbRef.child("tasks").child(taskKey).child("taskName").toString())
            }
        }

        return rootView
    }
}