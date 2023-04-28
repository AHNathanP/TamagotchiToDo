package com.example.tamagotchitodo

import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentPetBinding
import java.text.SimpleDateFormat

class PetFragment : Fragment() {
    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!
    lateinit var petName:String
    private val viewModel: StatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentPetBinding.inflate(inflater, container, false)
        val rootView = binding.root

        var calendar: Calendar = Calendar.getInstance()
        var simpleDateFormat: SimpleDateFormat = SimpleDateFormat("EEEE, LLLL dd, KK:mm aaa")
        var dateTime : String = simpleDateFormat.format(calendar.time).toString()
        binding.dateTimeTwo.text = dateTime

        petName = ""
        val petImageKey = viewModel.getPetImageKey()
        if (petImageKey!=0) {
            setImage(petImageKey)
        }

        setFragmentResultListener("REQUESTING_IMAGE_KEY") { requestKey: String, bundle: Bundle ->
            val imageKey = bundle.getInt("IMAGE_KEY")
            setImage(imageKey)
        }
        setFragmentResultListener("REQUESTING_NAME_KEY") { requestKeyTwo: String, bundleTwo: Bundle ->
            petName = bundleTwo.getString("NAME_KEY")?:""
            viewModel.setPetName(petName)
            binding.petStatusName.text = "$petName is:"
            setStatus()
        }

        binding.toDoListFragment.setOnClickListener {
            val action = PetFragmentDirections.actionPetFragmentToToDoListFragment()
            rootView.findNavController().navigate(action)
        }
        binding.petImage.setOnClickListener {
            val action = PetFragmentDirections.actionPetFragmentToChoosePetFragment()
            rootView.findNavController().navigate(action)
        }
        setStatus()
        return rootView
    }
    fun setStatus() {
        val name = viewModel.getPetName()
        val numOfTasksDone = viewModel.numOfTasksDone.value?:0
        var message = ""
        if (numOfTasksDone < 3 && name != "") {
            binding.petStatusName.text = "$name is:"
            message+= getString(R.string.pet_status_sad)
        }
        else if(numOfTasksDone in 3..9 && name != ""){
            binding.petStatusName.text = "$name is:"
            message+= getString(R.string.pet_status_happy)
        }
        else if(numOfTasksDone >= 10 && name!="") {
            binding.petStatusName.text = "$name is:"
            message += getString(R.string.pet_status_super_happy)
        }
        binding.petStatusFeeling.text = message
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