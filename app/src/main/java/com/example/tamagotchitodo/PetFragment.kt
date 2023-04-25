package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentPetBinding

class PetFragment : Fragment() {
    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentPetBinding.inflate(inflater, container, false)
        val rootView = binding.root

        setFragmentResultListener("REQUESTING_IMAGE_KEY") { requestKey: String, bundle: Bundle ->
            val imageKey = bundle.getInt("IMAGE_KEY")
            setImage(imageKey)
        }
        setFragmentResultListener("REQUESTING_NAME_KEY") { requestKeyTwo: String, bundleTwo: Bundle ->
            val petName = bundleTwo.getString("NAME_KEY")
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
        val numOfTasksDone = viewModel.numOfTasksDone.value?:0
        if (numOfTasksDone < 5) {
            binding.petStatusFeeling.text = getString(R.string.pet_status_sad)
        }
        else {
            binding.petStatusFeeling.text = getString(R.string.pet_status_happy)
        }
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