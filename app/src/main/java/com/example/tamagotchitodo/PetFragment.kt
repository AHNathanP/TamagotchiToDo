package com.example.tamagotchitodo

import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentPetBinding
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat

class PetFragment : Fragment() {
    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatusViewModel by activityViewModels()
    lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentPetBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference
        var key = ""
        var tasksDone = 0

        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEE, LLLL dd, KK:mm aaa")
        val dateTime : String = simpleDateFormat.format(calendar.time).toString()
        binding.dateTimeTwo.text = dateTime

        binding.toDoListFragment.setOnClickListener {
            Log.i("PetFragment inside button click listener", "tasksDone is $tasksDone")
            val action = PetFragmentDirections.actionPetFragmentToToDoListFragment(key, tasksDone)
            rootView.findNavController().navigate(action)
        }
        binding.petImage.setOnClickListener {
            val action = PetFragmentDirections.actionPetFragmentToChoosePetFragment()
            rootView.findNavController().navigate(action)
        }
        viewModel.updateWeekYear(SimpleDateFormat("w").format(calendar.time).toInt())

        dbRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val allDBEntries = snapshot.children

                var numOfPetsAdded = 0

                for (allPetsAdded in allDBEntries) {
                    for (singlePetEntry in allPetsAdded.children) {
                        if (singlePetEntry.child("nameOfPet").getValue() != null) {
                            numOfPetsAdded++
                            val petName = singlePetEntry.child("nameOfPet").getValue().toString()
                            val petStatus = singlePetEntry.child("status").getValue().toString()
                            val petImageId = Integer.parseInt(singlePetEntry.child("imageId").getValue().toString())
                            tasksDone = Integer.parseInt(singlePetEntry.child("numOfTasksDone").getValue().toString())
                            key = singlePetEntry.key.toString()
                            Log.i("PetFragment inside value event listener", "tasksDone is $tasksDone")

                            setStatus(petName, key, petStatus, tasksDone)
                            setImage(petImageId)
                        }
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w("PetFragment", "Failed to read value.", error.toException())
            }
        })

        return rootView
    }
    fun setStatus(name: String, key: String, status: String, tasksDone: Int) {
//        val tasks = viewModel.numOfTasksDone.value?:0
        var checkAllDates = true

        for (task in viewModel.listOfTasks.value?: mutableListOf()) {
            val month = task.monthDue
            val day = task.dayDue
            if(!(viewModel.checkTime(month, day)) && checkAllDates) {
                binding.petStatusName.text = "$name is:"
                dbRef.child("pets").child(key).child("status").setValue("super sad!")
                checkAllDates = false
            }
        }
        if (checkAllDates && name != "") {
            if (tasksDone < 3) {
                binding.petStatusName.text = "$name is:"
                dbRef.child("pets").child(key).child("status").setValue("sad...")
            }
            else if(tasksDone in 3..9){
                binding.petStatusName.text = "$name is:"
                dbRef.child("pets").child(key).child("status").setValue("happy!")
            }
        }
        binding.petStatusFeeling.text = status
    }
    fun setImage(imageKey: Int) {
        if (imageKey == 1) {
            binding.petImage.setImageResource(R.drawable.cat_pet)
        }
        if (imageKey == 2) {
            binding.petImage.setImageResource(R.drawable.lizard_pet)
        }
        if (imageKey == 3) {
            binding.petImage.setImageResource(R.drawable.pikachu_pet)
        }
    }
}