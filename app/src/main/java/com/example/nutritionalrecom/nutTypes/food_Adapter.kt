package com.example.nutritionalrecom.nutTypes

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionalrecom.databinding.FoodHolderBinding

class food_Adapter(var items: List<Items> = ArrayList<Items>(), val context: Context) : RecyclerView.Adapter<food_Adapter.MyViewHolder>() {

   /* class MyViewHolder(val binding: FoodHolderBinding) : RecyclerView.ViewHolder(binding.root)*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): food_Adapter.MyViewHolder {
        val binding = FoodHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: food_Adapter.MyViewHolder, position: Int) {
        holder.bind(items[position])
/*
        holder.binding.DESCKOR.text= items[position].DESC_KOR
        holder.binding.NUTRCONT1.text= items[position].NUTR_CONT1
        holder.binding.NUTRCONT3.text= items[position].NUTR_CONT3
        holder.binding.NUTRCONT5.text= items[position].NUTR_CONT5
        holder.binding.NUTRCONT7.text= items[position].NUTR_CONT7
*/

    }


    override fun getItemCount(): Int {
        return items.size
    }


    inner class MyViewHolder(private val binding: FoodHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        //위의 onCreateViewHolder에서 생성된 view를 가지고 실행함


       fun bind(item: Items) {

            binding.fooddata = item

        }



    }





}