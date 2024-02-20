package com.example.nutritionalrecom.nutTypes

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface food_Api {

    @GET("1471000/FoodNtrIrdntInfoService1/getFoodNtrItdntList1")
    suspend fun getFood ( @Query("desc_kor")desc_kor:String,
                          @Query("pageNo")pageNo:Int,
                          @Query("numOfRows")numOfRows:Int,
                          @Query("serviceKey")ServiceKey:String = "1p2hpJA1K3s46EN60ts0ipR8z9QL8tXFgdWp1wGdvh2Vm/q7GH6wJbA+RTncn6OrchSnXjhJKqgMY/+ujxhMNQ==",
                          @Query("type")type:String = "json",
                          @Query("resultCode")resultCode:String = "00",
                          @Query("resultMsg")resultMsg:String = "NORMAL SERVICE.",)
            : Response<food_DataClass>


}