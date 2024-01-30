package com.example.nutritionalrecom.nutTypes

import android.app.Dialog
import android.content.ContentValues
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritionalrecom.databinding.FragmentNutTypesBinding
import com.example.nutritionalrecom.loading_screen
import com.example.nutritionalrecom.nutCommunity.OnItemClick
import com.example.nutritionalrecom.nutCommunity.Vlog_Model
import com.example.nutritionalrecom.nutCommunity.vlog_Adapter

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

        //음식 리사이클러뷰
        food_RecyclerView("")

        val loadingAnimDialog = loading_screen(requireActivity())

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))


        //검색
        binding.foodSearch.isSubmitButtonEnabled = true
        binding.foodSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // @TODO
    /*            if (query != null) {
                    food_RecyclerView(query!!)
                }*/
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // @TODO
                loadingAnimDialog.show()
                food_RecyclerView(newText!!)
                loadingAnimDialog.dismiss()
                return true
            }
        })


        return binding.root
    }



    private fun food_RecyclerView(desc_Kor_Query : String) {

        val loadingAnimDialog = loading_screen(requireActivity())

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        viewModel.getPost(desc_Kor_Query,1,25)

        viewModel.myResponse.observe(this, Observer {

            if (it.isSuccessful) {
                val body = it.body()
                body?.let {
                    setAdapter(it.body.items as? MutableList<Items>)
                    loadingAnimDialog.dismiss()
                }
            }
            else{
                Toast.makeText(requireActivity(),it.code(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun setAdapter(items: MutableList<Items>?) {

        val mAdapter = food_Adapter(items,requireActivity(),this, requireActivity().application)
        binding.foodRecyclerView.adapter = mAdapter
        binding.foodRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
    }



}