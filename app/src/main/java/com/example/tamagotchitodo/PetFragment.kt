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

        val calendar: Calendar = Calendar.getInstance()
        val simpleDateFormat = SimpleDateFormat("EEE, LLLL dd, KK:mm aaa")
        val dateTime : String = simpleDateFormat.format(calendar.time).toString()
        binding.dateTimeTwo.text = dateTime

        binding.toDoListFragment.setOnClickListener {
            val action = PetFragmentDirections.actionPetFragmentToToDoListFragment(key)
            rootView.findNavController().navigate(action)
        }
        binding.petImage.setOnClickListener {
            val action = PetFragmentDirections.actionPetFragmentToChoosePetFragment()
            rootView.findNavController().navigate(action)
        }
        viewModel.updateWeekday(SimpleDateFormat("EEEE").format(calendar.time).toString(), key)

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
                            val tasksDone = Integer.parseInt(singlePetEntry.child("numOfTasksDone").getValue().toString())
                            key = singlePetEntry.key.toString()

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
        var checkAllDates = true

        for (task in viewModel.listOfTasks.value?: mutableListOf()) {
            val month = task.monthDue
            val day = task.dayDue
            if(!(checkTime(month, day)) && checkAllDates) {
                binding.petStatusName.text = "$name is:"
                dbRef.child("pets").child(key).child("status").setValue("super sad!")
                checkAllDates = false
            }
        }
        if (checkAllDates && name != "") {
            if (tasksDone < 1) {
                binding.petStatusName.text = "$name is:"
                dbRef.child("pets").child(key).child("status").setValue("sad...")
            }
            else {
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
    fun checkTime(monthOfDueDate: Int, dayOfDueDate: Int):Boolean {
        val calendar = Calendar.getInstance()
        val month : Int = SimpleDateFormat("L").format(calendar.time).toInt()
        val day : Int = SimpleDateFormat("d").format(calendar.time).toInt()

        if (monthOfDueDate < month) {
            return false
        }
        if (monthOfDueDate == month && dayOfDueDate < day) {
            return false
        }
        return true
    }
}