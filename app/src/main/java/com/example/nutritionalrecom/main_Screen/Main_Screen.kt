package com.example.nutritionalrecom.main_Screen

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.nutritionalrecom.MainActivity
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.ActivityMainBinding
import com.example.nutritionalrecom.databinding.FragmentMainScreenBinding
import com.example.nutritionalrecom.databinding.FragmentNutSleepBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class Main_Screen : Fragment() {

    private var _binding : FragmentMainScreenBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val handler = Handler(Looper.getMainLooper()) {
        setPage()
        true
    }

    var currentPosition = 0


    private val main_activity: MainActivity by lazy {
        activity as MainActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)

        val adapter11 = ViewPagerAdapter()
        binding.viewpager11.adapter = adapter11

        CoroutineScope(Dispatchers.Main).launch {
            while (true) {
                delay(2000)
                setPage()
            }
        } //뷰페이저

        binding.foodCal.setOnClickListener {
            main_activity.onFragmentChanged(0)
        }

        return binding.root
    }

    private fun setPage() {
        if(currentPosition == 3 ) {
            currentPosition = 0
        }
        if(binding.viewpager11 != null) {
            binding.viewpager11.setCurrentItem(currentPosition, true)
        }

        currentPosition += 1
    }



}