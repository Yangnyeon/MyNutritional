package com.example.nutritionalrecom.nutCommunity

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class vlog_Repository
@Inject
constructor(application: Application) {


    private val vlog_Dao: vlog_Dao
    init {
        var db = vlog_Module.get_vlog_DB(application)
        vlog_Dao = db!!.vlog_Dao()
    }

    fun insert(vlog_Model: Vlog_Model) {
        vlog_Dao.insert(vlog_Model)
    }

    fun updateMemo(vlog_Model: Vlog_Model){
        vlog_Dao.update(vlog_Model)
    }

    fun delete(vlog_Model: Vlog_Model){
        vlog_Dao.delete(vlog_Model)
    }

    fun getAll(): LiveData<List<Vlog_Model>> {
        return vlog_Dao.getAll()
    }

    fun readDateData(year : Int, month : Int, day : Int): Flow<List<Vlog_Model>> {
        return vlog_Dao.readDateData(year, month, day)
    }


    fun searchDatabase(searchQuery: String): Flow<List<Vlog_Model>> {
        return vlog_Dao.searchDatabase(searchQuery)
    }
}