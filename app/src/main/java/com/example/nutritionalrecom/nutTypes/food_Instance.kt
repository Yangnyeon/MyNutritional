package com.example.nutritionalrecom.nutTypes

import com.example.nutritionalrecom.nutTypes.food_Address.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object food_Instance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api : food_Api by lazy {
        retrofit.create(food_Api::class.java)
    }

}