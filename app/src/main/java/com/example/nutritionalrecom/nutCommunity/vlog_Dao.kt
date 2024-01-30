package com.example.nutritionalrecom.nutCommunity

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface vlog_Dao {
    @Query("SELECT * FROM Vlog_Table ORDER BY id DESC")
    fun getAll(): LiveData<List<Vlog_Model>>

    /*
    @Query("SELECT * FROM Cigarette ORDER BY id DESC")
    fun selectList(page: Int, loadSize: Int): LiveData<List<Cigarette>>

     */

    @Insert
    fun insert(vlog_Model: Vlog_Model)

    @Update
    fun update(vlog_Model: Vlog_Model)

    @Delete
    fun delete(vlog_Model: Vlog_Model)

    @Query("SELECT * FROM Vlog_Table WHERE year = :year AND month = :month AND day = :day ORDER BY id DESC")
    fun readDateData(year : Int, month : Int ,day : Int) : Flow<List<Vlog_Model>>

    @Query("SELECT * FROM Vlog_Table ORDER BY year DESC, month DESC, day DESC, id DESC")
    fun readAllData() : Flow<List<Vlog_Model>>

    @Query("SELECT * FROM Vlog_Table WHERE content LIKE :searchQuery ORDER BY id DESC")
    fun searchDatabase(searchQuery : String) : Flow<List<Vlog_Model>>

}