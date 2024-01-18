package com.example.nutritionalrecom.nutTypes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentNutTypesBinding

class nutTypesFragment : Fragment() {

    private var _binding : FragmentNutTypesBinding ?= null    // 뷰 바인딩
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutTypesBinding.inflate(inflater, container, false)

        return binding.root
    }

}