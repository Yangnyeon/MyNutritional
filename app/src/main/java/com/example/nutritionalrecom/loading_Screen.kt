package com.example.nutritionalrecom

import android.app.Dialog
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.nutritionalrecom.databinding.ActivityLoadingScreenBinding
import com.example.nutritionalrecom.databinding.ActivityMainBinding

class loading_screen(context: Context): Dialog(context) {

    private var mBinding: ActivityLoadingScreenBinding? = null
    private val binding get() = mBinding!!

    lateinit var turnAround : Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mBinding = ActivityLoadingScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)

        turnAround = AnimationUtils.loadAnimation(context, R.anim.turn_around)

        binding.loadingImage.startAnimation(turnAround)
    }
}