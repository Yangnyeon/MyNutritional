package com.example.nutritionalrecom.nutTypes

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritionalrecom.databinding.FragmentNutTypesBinding

class nutTypesFragment : Fragment() {

    private lateinit var viewModel : food_ViewModel

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

        val repository = food_Repository()
        val viewModelFactory = food_ViewModel_Factory(repository)
        viewModel = ViewModelProvider(this,viewModelFactory).get(food_ViewModel::class.java)

        viewModel.getPost("바나나",1,25)

        viewModel.myResponse.observe(this, Observer {
            Log.d(ContentValues.TAG, "야야야::$it")


            if (it.isSuccessful) {
                val body = it.body()
                Log.d(ContentValues.TAG, "Test::$body")
                body?.let {
                    setAdapter(it.body.items as MutableList<Items>)
                }

            }

            else{
                Toast.makeText(requireActivity(),it.code(), Toast.LENGTH_SHORT).show()
            }
        })



        return binding.root
    }

    private fun setAdapter(items : MutableList<Items>) {
        val mAdapter = food_Adapter(items,requireActivity())
        binding.foodRecyclerView.adapter = mAdapter
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }

}