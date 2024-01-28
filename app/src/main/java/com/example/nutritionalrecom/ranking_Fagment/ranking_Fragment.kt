package com.example.nutritionalrecom.ranking_Fagment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentRankingBinding
import com.example.nutritionalrecom.diet_Food.diet_Food_Adapter
import com.example.nutritionalrecom.loading_screen
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class ranking_Fragment : Fragment() {

    private var _binding : FragmentRankingBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRankingBinding.inflate(inflater, container, false)

        var top_Count = 1

        val loadingAnimDialog = loading_screen(requireActivity())

        loadingAnimDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        loadingAnimDialog.show()

        FirebaseFirestore.getInstance().collection("Ranking")
            .orderBy("Run_Count", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { result ->

                loadingAnimDialog.dismiss()

                for(document in result) {
                    val user = result.toObjects(Ranking_Model::class.java)
                    Log.d("등수확인", document.getLong("Run_Count").toString())
                    binding.rankingRecyclerView.adapter = ranking_Adapter(requireActivity(),user)
                    when (top_Count) {
                        1 -> {
                            binding.winnerRunCount.text = document.getLong("Run_Count").toString() + " 걸음수"
                            binding.winnerId.text = document.getString("NickName").toString()

                            Glide.with(requireActivity())
                                .load(document.getString("ranking_Image"))
                                .placeholder(R.mipmap.ic_launcher)
                                .circleCrop()
                                .error(R.mipmap.ic_launcher_round)
                                .into(binding.winnerImage)
                        }
                        2 -> {
                            binding.secondRunCount.text = document.getLong("Run_Count").toString() + " 걸음수"
                            binding.secondId.text = document.getString("NickName").toString()

                            Glide.with(requireActivity())
                                .load(document.getString("ranking_Image"))
                                .placeholder(R.mipmap.ic_launcher)
                                .circleCrop()
                                .error(R.mipmap.ic_launcher_round)
                                .into(binding.secondImage)
                        }
                        3 -> {
                            binding.thirdRunCount.text = document.getLong("Run_Count").toString() + " 걸음수"
                            binding.thirdId.text = document.getString("NickName").toString()

                            Glide.with(requireActivity())
                                .load(document.getString("ranking_Image"))
                                .placeholder(R.mipmap.ic_launcher)
                                .circleCrop()
                                .error(R.mipmap.ic_launcher_round)
                                .into(binding.thirdImage)
                        }
                        // 추가적인 경우가 있다면 여기에 계속해서 추가할 수 있습니다.
                        else -> {
                            // 예상치 못한 top_Count 값에 대한 처리를 추가할 수 있습니다.
                        }
                    }

                    top_Count++
                }
            }
            .addOnFailureListener {
                Toast.makeText(requireActivity(), "실패", Toast.LENGTH_SHORT).show()
            }

        binding.rankingRecyclerView.apply {
            /*layoutManager =  GridLayoutManager(requireActivity(), 2)*/
            layoutManager =  LinearLayoutManager(requireActivity())

        }

        return binding.root
    }

}