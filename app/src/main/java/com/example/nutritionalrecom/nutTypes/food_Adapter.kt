package com.example.nutritionalrecom.nutTypes

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionalrecom.databinding.FoodHolderBinding

class food_Adapter(var items: MutableList<Items>? = ArrayList<Items>(), val context: Context) : RecyclerView.Adapter<food_Adapter.MyViewHolder>() {

   /* class MyViewHolder(val binding: FoodHolderBinding) : RecyclerView.ViewHolder(binding.root)*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): food_Adapter.MyViewHolder {
        val binding = FoodHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: food_Adapter.MyViewHolder, position: Int) {
        holder.bind((items?.get(position) ?: 0) as Items)
    }


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }


    inner class MyViewHolder(private val binding: FoodHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        //위의 onCreateViewHolder에서 생성된 view를 가지고 실행함


       fun bind(item: Items) {

            binding.fooddata = item

        }



    }





}