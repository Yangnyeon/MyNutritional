package com.example.nutritionalrecom.nutTypes

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface food_Api {

    @GET(??)
    suspend fun getFood ( @Query("desc_kor")desc_kor:String,
                          @Query("pageNo")pageNo:Int,
                          @Query("numOfRows")numOfRows:Int,
                          @Query("serviceKey")ServiceKey:String = ??
                          @Query("type")type:String = "json",
                          @Query("resultCode")resultCode:String = "00",
                          @Query("resultMsg")resultMsg:String = "NORMAL SERVICE.",)
            : Response<food_DataClass>


}
