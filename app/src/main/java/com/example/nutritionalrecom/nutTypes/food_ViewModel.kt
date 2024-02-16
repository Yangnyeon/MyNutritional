package com.example.nutritionalrecom.nutTypes

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class food_ViewModel @Inject constructor(private val repository : food_Repository) : ViewModel() {

    val myResponse : MutableLiveData<Response<food_DataClass>> = MutableLiveData()


    fun getPost(desc_kor: String, pageNo: Int, numOfRows: Int) {
        viewModelScope.launch {
            val response = repository.getPost(desc_kor, pageNo, numOfRows)
            myResponse.value = response
        }
    }


}