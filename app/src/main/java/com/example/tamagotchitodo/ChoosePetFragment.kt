package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentChoosePetBinding

class ChoosePetFragment : Fragment() {
    private var _binding: FragmentChoosePetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: StatusViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentChoosePetBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.petChoiceOne.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
            setFragmentResult("REQUESTING_IMAGE_KEY", bundleOf("IMAGE_KEY" to 1))
            setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to petName))
            viewModel.setPetImageKey(1)
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceTwo.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
            setFragmentResult("REQUESTING_IMAGE_KEY", bundleOf("IMAGE_KEY" to 2))
            setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to petName))
            viewModel.setPetImageKey(2)
            rootView.findNavController().navigateUp()
        }
        binding.petChoiceThree.setOnClickListener {
            val petName = binding.petNameEdit.text.toString()
            setFragmentResult("REQUESTING_IMAGE_KEY", bundleOf("IMAGE_KEY" to 3))
            setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to petName))
            viewModel.setPetImageKey(3)
            rootView.findNavController().navigateUp()
        }

        return rootView
    }
}