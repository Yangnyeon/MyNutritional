package com.example.nutritionalrecom.diet_Food

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.view.setPadding
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritionalrecom.MainActivity
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentDietFoodBinding
import com.example.nutritionalrecom.databinding.FragmentNutCommunityBinding
import com.example.nutritionalrecom.loading_screen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class diet_Food_Fragment : Fragment() {

    private var _binding : FragmentDietFoodBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    val fire_Db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    val itemList = arrayListOf<Food_Model>()

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

        FirebaseFirestore.getInstance().collection("Food")
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
            layoutManager =  GridLayoutManager(requireActivity(), 2)
        }



        binding.rankingRegs.setOnClickListener {
            val doc = UUID.randomUUID().toString()

            val ranking_Data = hashMapOf(
                "Food_Image" to "https://cdn.pixabay.com/photo/2014/04/05/11/22/agriculture-315512_960_720.jpg",
                "Food_Doc" to doc,
                "Food_Kcal" to 0,
                "Food_Name" to "이름",
                "Food_ServingSize" to 0
            )

            fire_Db.collection("Food").document(doc)
                .set(ranking_Data)
                .addOnSuccessListener {
                }
                .addOnFailureListener { e ->
                    Toast.makeText(
                        requireActivity(),
                        "${e}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d("에러" ,e.toString())
                }
        }



        binding.CommunitySearchview.setOnQueryTextListener(searchViewTextListener)


        var intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            startActivity(intent)
        }

        return binding.root
    }

    private var searchViewTextListener: androidx.appcompat.widget.SearchView.OnQueryTextListener =
        object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            //검색버튼 입력시 호출, 검색버튼이 없으므로 사용하지 않음
            override fun onQueryTextSubmit(s: String): Boolean {
                return true
            }

            //텍스트 입력/수정시에 호출
            override fun onQueryTextChange(s: String): Boolean {

                FirebaseFirestore.getInstance().collection("Food")
                    .get()
                    .addOnSuccessListener { result ->
                        itemList.clear()
                        for (document in result) {
                            if (document.getString("Food_Name").toString()!!.contains(s)) {
                                var search_Food =
                                    Food_Model(
                                        document["Food_Name"] as String,
                                        document["Food_Image"] as String,
                                        document["Food_Doc"] as String,
                                        document["Food_Kcal"] as Long,
                                        document["Food_ServingSize"] as Long
                                    )
                                itemList.add(search_Food)
                            }
                        }
                        binding.FoodRecyclerView.adapter =
                            diet_Food_Adapter(requireActivity(), itemList)
                    }
                    .addOnFailureListener {
                        Toast.makeText(requireActivity(), "실패", Toast.LENGTH_SHORT).show()
                    }
                binding.FoodRecyclerView.apply {
                    layoutManager = GridLayoutManager(requireActivity(), 2)
                }
                return true
            }
        }

}