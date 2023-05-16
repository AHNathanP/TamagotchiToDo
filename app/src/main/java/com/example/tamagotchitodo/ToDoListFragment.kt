package com.example.tamagotchitodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentToDoListBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import androidx.fragment.app.activityViewModels
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.text.SimpleDateFormat
import java.util.*


class ToDoListFragment : Fragment() {
    private var _binding: FragmentToDoListBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference
    lateinit var taskList: MutableList<Task>
    private val viewModel: StatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentToDoListBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference

        taskList = mutableListOf()
        val myAdapter = TaskAdapter(taskList, viewModel)
        binding.recyclerView.adapter = myAdapter

//        var newTaskName = ""                                                                        non-firebase stuff
//        var newTaskDueMonth = 0
//        var newTaskDueDay = 0
//
//        setFragmentResultListener("REQUESTING_NAME_KEY") { nameKey: String, bundle: Bundle ->
//            newTaskName = bundle.getString("NAME_KEY")?:""
//        }
//        setFragmentResultListener("REQUESTING_MONTH_KEY") { monthKey: String, bundle: Bundle ->
//            newTaskDueMonth = bundle.getInt("MONTH_KEY")
//        }
//        setFragmentResultListener("REQUESTING_DAY_KEY") { dayKey: String, bundle: Bundle ->
//            newTaskDueDay = bundle.getInt("DAY_KEY")
//            val newTask = Task(newTaskName, newTaskDueMonth, newTaskDueDay)
//            viewModel.addTask(newTask)                                                              non-firebase stuff
//        }

        binding.addButton.setOnClickListener {
            val action = ToDoListFragmentDirections.actionToDoListFragmentToAddFragment()
            rootView.findNavController().navigate(action)
        }
        binding.backButton.setOnClickListener {
            rootView.findNavController().navigateUp()
        }

//        val taskList = viewModel.listOfTasks.value?: mutableListOf()                                non-firebase stuff
//        val myAdapter = TaskAdapter(taskList, viewModel)
//        binding.recyclerView.adapter = myAdapter

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
                            val currentTask = Task(taskName, taskMonthDue, taskDayDue)
                            taskList.add(currentTask)
                            viewModel.addTask(currentTask)
                            notificationManager(taskMonthDue, taskDayDue)
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

    fun notificationManager(monthDue: Int, dayDue: Int) {
        var month : Int = SimpleDateFormat("L").format(Calendar.getInstance().time).toInt()
        var day : Int = SimpleDateFormat("d").format(Calendar.getInstance().time).toInt()
        if (monthDue==month && dayDue - day == 3) {
            var builder = NotificationCompat.Builder(requireContext(), "")
                .setSmallIcon(R.drawable.app_icon_foreground)
                .setContentTitle("Task due soon!")
                .setContentText("You have stuff to do soon!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(requireContext())) {
                notify(0, builder.build())
            }
        }
    }
}