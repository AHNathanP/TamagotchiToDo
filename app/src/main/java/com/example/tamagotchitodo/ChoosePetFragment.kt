package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentChoosePetBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ChoosePetFragment : Fragment() {
    private var _binding: FragmentChoosePetBinding? = null
    private val binding get() = _binding!!
    lateinit var dbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentChoosePetBinding.inflate(inflater, container, false)
        val rootView = binding.root
        dbRef = Firebase.database.reference

        binding.petChoiceOne.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
            val pet = Pet(petName, getString(R.string.pet_status_sad), 1, 0)
            dbRef.child("pets").push().setValue(pet)
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceTwo.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
            val pet = Pet(petName, getString(R.string.pet_status_sad), 2, 0)
            dbRef.child("pets").push().setValue(pet)
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceThree.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
            val pet = Pet(petName, getString(R.string.pet_status_sad), 3, 0)
            dbRef.child("pets").push().setValue(pet)
            rootView.findNavController().navigateUp()
        }

        return rootView
    }
}