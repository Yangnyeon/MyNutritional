package com.example.nutritionalrecom.nutCommunity

import android.app.Application
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class vlog_ViewModel  @Inject constructor(private val repository : vlog_Repository) : AndroidViewModel(Application()) {

    private var _currentData = MutableLiveData<List<Vlog_Model>>()


    fun insert(cigarette: Vlog_Model) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(cigarette)
        }
    }

    fun delete(cigarette: Vlog_Model){
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(cigarette)
        }
    }

    fun getAll(): LiveData<List<Vlog_Model>> {

        return repository.getAll()
    }

    fun updateMemo(cigarette : Vlog_Model){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateMemo(cigarette)
        }
    }


    fun readDateData(year : Int, month : Int ,day : Int) : LiveData<List<Vlog_Model>> {
        return repository.readDateData(year, month, day).asLiveData()
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Vlog_Model>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}