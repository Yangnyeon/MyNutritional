package com.example.nutritionalrecom.nutTypes

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class food_Module {

    companion object {
        @Singleton
        @Provides
        fun getRetroInstance(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(food_Address.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        @Singleton
        @Provides
        fun getPost() : food_Api {

            //return Cancer_Instance.api.getAlbums(perpage,per,current)
            return getRetroInstance().create(food_Api::class.java)
        }

    }

}