package com.example.nutritionalrecom.nutCommunity

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.ActivityCheckVlogBinding
import com.example.nutritionalrecom.databinding.ActivityMainBinding

class check_VLOG(context : Context, OnItemClick : OnItemClick, var desc_Kor : String, var SERVING_WT : String,
                 var NUTR_CONT1 : String, var NUTR_CONT3 : String,var NUTR_CONT5 : String,var NUTR_CONT7 : String) : Dialog(context), View.OnClickListener   {

    private var _binding : ActivityCheckVlogBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    var ItemClick : OnItemClick ?= null

    init {
        this.ItemClick = OnItemClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityCheckVlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.DESCKOR.text = desc_Kor
        binding.SERVINGWT.text = "1회제공량 : " + SERVING_WT + " g"
        binding.NUTRCONT1.text = "칼로리 : " + NUTR_CONT1 + " kcal"
        binding.NUTRCONT3.text = "단백질 : " + NUTR_CONT3 + " g"
        binding.NUTRCONT5.text = "지방 : " + NUTR_CONT5 + " g"
        binding.NUTRCONT7.text = "콜레스테롤 : " + NUTR_CONT7 + " mg"

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.memoBtn.setOnClickListener {
          /*  this.ItemClick?.check_memo(binding.memoContent.text.toString(), this)*/
            this.ItemClick?.check_memo(this)
        }

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}