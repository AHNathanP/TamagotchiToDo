package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentChoosePetBinding

class ChoosePetFragment : Fragment() {
    private var _binding: FragmentChoosePetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentChoosePetBinding.inflate(inflater, container, false)
        val rootView = binding.root
        val petName = binding.petNameEdit.text.toString()

        binding.petChoiceOne.setOnClickListener {
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceTwo.setOnClickListener {
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceThree.setOnClickListener {
            rootView.findNavController().navigateUp()
        }

        return rootView
    }
}