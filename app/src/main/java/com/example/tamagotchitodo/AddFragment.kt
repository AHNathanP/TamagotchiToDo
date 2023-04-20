package com.example.tamagotchitodo

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.findNavController
import com.example.tamagotchitodo.databinding.FragmentAddBinding

class AddFragment : Fragment() {
    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        val rootView = binding.root

        binding.addButtonAddFrag.setOnClickListener {
            val taskName = binding.newTaskName.text.toString()
            setFragmentResult("REQUESTING_NAME_KEY", bundleOf("NAME_KEY" to taskName))
            rootView.findNavController().navigateUp()
        }

        return rootView
    }
}