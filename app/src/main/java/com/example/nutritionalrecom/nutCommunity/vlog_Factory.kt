package com.example.nutritionalrecom.nutCommunity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class vlog_Factory(private val repository : vlog_Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return vlog_ViewModel(repository) as T
    }

}
