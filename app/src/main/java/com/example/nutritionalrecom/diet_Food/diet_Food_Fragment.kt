package com.example.nutritionalrecom.diet_Food

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentDietFoodBinding
import com.example.nutritionalrecom.databinding.FragmentNutCommunityBinding
import com.example.nutritionalrecom.loading_screen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.text.SimpleDateFormat
import java.util.*

class diet_Food_Fragment : Fragment() {

    private var _binding : FragmentDietFoodBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDietFoodBinding.inflate(inflater, container, false)

        val loadingAnimDialog = loading_screen(requireActivity())

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        FirebaseFirestore.getInstance().collection("Ranking")
            .orderBy("Run_Count", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->

                loadingAnimDialog.dismiss()

                for(document in result) {
                    val user = result.toObjects(Food_Model::class.java)
                    binding.FoodRecyclerView.adapter = diet_Food_Adapter(requireActivity(),user)
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "실패", Toast.LENGTH_SHORT).show()
            }

        binding.FoodRecyclerView.apply {
            /*layoutManager =  GridLayoutManager(requireActivity(), 2)*/
            layoutManager =  LinearLayoutManager(requireActivity())

        }



        return binding.root
    }

}