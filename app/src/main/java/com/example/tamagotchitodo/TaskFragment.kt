package com.example.tamagotchitodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentTaskBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.snapshots
import com.google.firebase.ktx.Firebase

class TaskFragment : Fragment() {
    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference
    private val viewModel: StatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference
        val args = TaskFragmentArgs.fromBundle(requireArguments())
        binding.taskName.text = args.taskNameArg
        binding.taskDueByDate.text = args.taskDueDateArg

        binding.deleteButton.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle(R.string.alert_title)
                .setMessage(R.string.alert_message)
                .setPositiveButton(R.string.alert_positive) { dialog, which ->
//                    dbRef.addValueEventListener(object: ValueEventListener {
//                        override fun onDataChange(snapshot: DataSnapshot) {
//                            val allDBEntries = snapshot.children
//
//                            for (allTaskEntries in allDBEntries) {
//                                for (singleTaskEntry in allTaskEntries.children) {
//                                    val dueDate = "${singleTaskEntry.child("monthDue")}/${singleTaskEntry.child("dayDue")}"
//                                    if (singleTaskEntry.child("taskName").toString() == args.taskNameArg &&
//                                            dueDate == args.taskDueDateArg) {
//                                        var key = singleTaskEntry.key
//                                        Log.i("TaskFragment", "Key is $key")
//                                        if (key != null) {
//                                            dbRef.child("tasks").child(key).removeValue()
//                                        }
//                                    }
//                                }
//                            }
//                        }
//
//                        override fun onCancelled(error: DatabaseError) {
//                            Log.w("TaskFragment", "Failed to read value.", error.toException())
//                        }
//                    })
                    val index = viewModel.taskKey.value?:0
                    viewModel.deleteTaskWithoutUpdate(index)
                    rootView.findNavController().navigateUp()
                }
                .setNegativeButton(R.string.alert_negative) { dialog, which ->
                    rootView.findNavController().navigateUp()
                }
                .show()
        }
        binding.doneButton.setOnClickListener {
            val index = viewModel.taskKey.value?:0
            viewModel.deleteTask(index)
            rootView.findNavController().navigateUp()
        }

        return rootView
    }

}