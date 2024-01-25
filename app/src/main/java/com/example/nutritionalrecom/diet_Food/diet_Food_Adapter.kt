package com.example.nutritionalrecom.diet_Food

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.DietFoodLayoutBinding

class diet_Food_Adapter (val context: Context, var Food_items: List<Food_Model>) : RecyclerView.Adapter<diet_Food_Adapter.TodoViewHolder>() {

    //private val nutrient_items = ArrayList<nutrient_model>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = DietFoodLayoutBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return Food_items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(Food_items[position])

        holder.itemView.setOnClickListener {
          /*  var Food_bottom_sheet = Food_Bottom_Sheet()

            var Food_doc_bundle = bundleOf(
                "Food_Doc" to Food_items[position].Food_Doc,
                "Food_Content" to Food_items[position].Food_Content
            )

            Food_bottom_sheet.arguments = Food_doc_bundle

            Food_bottom_sheet.show((context as Rx_java_tranning).supportFragmentManager, Food_bottom_sheet.tag)*/

        }

    }


    inner class TodoViewHolder(private val binding: DietFoodLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Food_Model) {


            binding.foodHolerName.text = item.NickName
            binding.foodCount.text = item.Run_Count.toString()

           /* Glide.with(context)
                .load(uploadCurrent.Food_Image)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .error(R.mipmap.ic_launcher_round)
                .into(binding.foodFoodHolderImage)*/
        }
    }
}