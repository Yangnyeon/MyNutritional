package com.example.nutritionalrecom.nutTest

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentNutCommunityBinding
import com.example.nutritionalrecom.databinding.FragmentNutTestBinding


class nutTestFragment : Fragment() {

    private var _binding : FragmentNutTestBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutTestBinding.inflate(inflater, container, false)

        return binding.root
    }

}