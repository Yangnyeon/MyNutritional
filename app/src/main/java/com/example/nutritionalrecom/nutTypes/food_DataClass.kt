package com.example.nutritionalrecom.nutTypes

import com.google.gson.annotations.SerializedName


class food_DataClass (

    @SerializedName("header") var header:Header,

    @SerializedName("body") var body:Body,
){
    override fun toString(): String {
        return "food_DataClass(header=$header, body=$body)"
    }
}



data class Header (
    @SerializedName("resultCode") var resultCode : String,
    @SerializedName("resultMsg") var resultMsg : String,
){
    override fun toString(): String {
        return "Header(resultCode='$resultCode', resultMsg='$resultMsg')"
    }
}


data class Body (

    var pageNo:Int,

    var totalCount: Int,

    var numOfRows : Int,

    var items:MutableList<Items>,

) {
    override fun toString(): String {
        return "Body(pageNo=$pageNo, totalCount=$totalCount, numOfRows=$numOfRows, items=$items)"
    }
}

data class Items (
    @SerializedName("DESC_KOR") var DESC_KOR:String?, //이름
    @SerializedName("SERVING_WT") val SERVING_WT :String?, //1회제공량
    @SerializedName("NUTR_CONT1") val NUTR_CONT1 :String? , //칼로리
    @SerializedName("NUTR_CONT3") val NUTR_CONT3 :String?, //단백질
    @SerializedName("NUTR_CONT4") val NUTR_CONT5 :String?, //지방
    @SerializedName("NUTR_CONT7") val NUTR_CONT7 :String?, //콜레스테롤*/
    @SerializedName("ANIMAL_PLANT") val ANIMAL_PLANT :String?, //가공업체*/
) {
    override fun toString(): String {
        return "items(DESC_KOR=$DESC_KOR, SERVING_WT=$SERVING_WT, NUTR_CONT1=$NUTR_CONT1, NUTR_CONT3=$NUTR_CONT3, NUTR_CONT5=$NUTR_CONT5, NUTR_CONT7=$NUTR_CONT7)"
    }
}
