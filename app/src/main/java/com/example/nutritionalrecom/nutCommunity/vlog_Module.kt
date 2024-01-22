package com.example.nutritionalrecom.nutCommunity

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class vlog_Module {
    companion object {
        @Singleton
        @Provides
        fun get_vlog_DB(context: Application): vlog_Database {
            return vlog_Database.getInstance(context)!!
        }

        @Singleton
        @Provides
        fun get_Dao(vlog_Database: vlog_Database): vlog_Dao {
            return vlog_Database.vlog_Dao()
        }
    }

}