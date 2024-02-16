package com.example.nutritionalrecom.ranking_Fagment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.LankingLayoutBinding
import com.google.firebase.storage.FirebaseStorage

class ranking_Adapter  (val context: Context, var ranking_List: List<Ranking_Model>) : RecyclerView.Adapter<ranking_Adapter.TodoViewHolder>() {

    //private val nutrient_items = ArrayList<nutrient_model>()

    var ranking_NumberList = ArrayList<Int>()

    init {
        ranking_List = ranking_List.drop(3)
    }


    init  {

        for(i in 1 .. ranking_List.size + 1) {
            ranking_NumberList.add(i + 3)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = LankingLayoutBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
       holder.bind(ranking_List[position])

        holder.ranking_Count.text = ranking_NumberList[position].toString()

        holder.itemView.setOnClickListener {


        }

    }


    override fun getItemCount(): Int {
        return ranking_List.size
    }


    inner class TodoViewHolder(private val binding: LankingLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        var ranking_Count = binding.rankingNumber

        fun bind(item: Ranking_Model) {
            binding.ranking = item

            Glide.with(context)
                .load(ranking_List[position].ranking_Image)
                .circleCrop()
                .placeholder(R.mipmap.ic_launcher)
                .into(binding.rankingImage)
        }



    }
}