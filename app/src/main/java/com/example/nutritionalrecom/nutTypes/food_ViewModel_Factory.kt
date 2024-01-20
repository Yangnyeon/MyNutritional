package com.example.nutritionalrecom.nutTypes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class food_ViewModel_Factory(private val repository : food_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return food_ViewModel(repository) as T
    }
}