package com.example.tamagotchitodo

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentChoosePetBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChoosePetFragment : Fragment() {
    private var _binding: FragmentChoosePetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatusViewModel by activityViewModels()
    lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentChoosePetBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference

        binding.petChoiceOne.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
//            setFragmentResult("REQUESTING_IMAGE_KEY", bundleOf("IMAGE_KEY" to 1))                  non-firebase stuff
//            setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to petName))
//            viewModel.setPetImageKey(1)
            val pet = Pet(petName, getString(R.string.pet_status_sad), 1)
            dbRef.child("pet").push().setValue(pet)
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceTwo.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
//            setFragmentResult("REQUESTING_IMAGE_KEY", bundleOf("IMAGE_KEY" to 2))                  non-firebase stuff
//            setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to petName))
//            viewModel.setPetImageKey(2)
            val pet = Pet(petName, getString(R.string.pet_status_sad), 2)
            dbRef.child("pet").push().setValue(pet)
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceThree.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
//            setFragmentResult("REQUESTING_IMAGE_KEY", bundleOf("IMAGE_KEY" to 3))                  non-firebase stuff
//            setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to petName))
//            viewModel.setPetImageKey(3)
            val pet = Pet(petName, getString(R.string.pet_status_sad), 3)
            dbRef.child("pet").push().setValue(pet)
            rootView.findNavController().navigateUp()
        }

        return rootView
    }
}