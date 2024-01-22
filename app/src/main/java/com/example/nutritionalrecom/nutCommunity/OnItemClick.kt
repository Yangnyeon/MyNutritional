package com.example.nutritionalrecom.nutCommunity

import android.app.Dialog

interface OnItemClick {

    fun deleteTodo(vlog: Vlog_Model)

    fun check_memo(content : String, dialog : Dialog)

}