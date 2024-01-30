package com.example.nutritionalrecom.nutCommunity

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Vlog_Model::class], version = 3, exportSchema = true)
abstract class vlog_Database : RoomDatabase() {

    abstract fun vlog_Dao(): vlog_Dao

    companion object {
        private var instance: vlog_Database? = null

        @Synchronized
        fun getInstance(context: Context): vlog_Database? {
            if (instance == null) {
                synchronized(vlog_Database::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        vlog_Database::class.java,
                        "Vlog_Table"
                    ).fallbackToDestructiveMigration()
                    .build()
                }
            }
            return instance
        }
    }
}