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

class check_VLOG(context : Context, OnItemClick : OnItemClick) : Dialog(context), View.OnClickListener   {

    private var _binding : ActivityCheckVlogBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    var ItemClick : OnItemClick ?= null

    init {
        this.ItemClick = OnItemClick
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_vlog)

        _binding = ActivityCheckVlogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        binding.memoBtn.setOnClickListener {
            this.ItemClick?.check_memo(binding.memoContent.text.toString(), this)
        }

    }

    override fun onClick(p0: View?) {
        TODO("Not yet implemented")
    }
}