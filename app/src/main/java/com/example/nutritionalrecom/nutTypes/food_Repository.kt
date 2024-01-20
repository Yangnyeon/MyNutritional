package com.example.nutritionalrecom.nutTypes

import android.content.ContentValues
import android.util.Log
import retrofit2.Response
import javax.inject.Inject

class food_Repository @Inject constructor() {


    private val food_API: food_Api

    init {
        var db = food_Module.getPost()
        food_API = db!!
    }

    suspend fun getPost(desc_kor : String, pageNo : Int, numOfRows : Int) : Response<food_DataClass> {
        return food_API.getFood(desc_kor,pageNo,numOfRows)
    }


}