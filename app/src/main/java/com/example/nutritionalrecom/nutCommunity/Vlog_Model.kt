package com.example.nutritionalrecom.nutCommunity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Vlog_Table")
class Vlog_Model(
    var content: String,
    var Year : Int,
    var Month : Int,
    var Day : Int,
    var eng_Month : String,
    var kcal : String,
    var time : String
){
    @PrimaryKey(autoGenerate = true) var id: Int = 0
}