package com.example.nutritionalrecom.nutCommunity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nutritionalrecom.databinding.FragmentNutCommunityBinding


class nutCommunityFragment : Fragment() {

    private var _binding : FragmentNutCommunityBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutCommunityBinding.inflate(inflater, container, false)

        return binding.root
    }

}