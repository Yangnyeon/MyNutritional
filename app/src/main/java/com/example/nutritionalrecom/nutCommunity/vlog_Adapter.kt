package com.example.nutritionalrecom.nutCommunity

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionalrecom.databinding.VlotItemBinding
import java.util.ArrayList

class vlog_Adapter(listener: OnItemClick) : RecyclerView.Adapter<vlog_Adapter.TodoViewHolder>() {

    private val mCallback = listener

    private val items = ArrayList<Vlog_Model>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : TodoViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = VlotItemBinding.inflate(layoutInflater)
        return TodoViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(items[position])
        // holder.bind123(itemList[position])

        holder.itemView.setOnClickListener {

        }
    }


    inner class TodoViewHolder(private val binding: VlotItemBinding): RecyclerView.ViewHolder(binding.root){

        fun bind(cloths : Vlog_Model) {

            binding.cloths = cloths

            binding.delete.setOnClickListener {
                mCallback.deleteTodo(cloths)
            }


        }
    }

    fun setList(englishes: List<Vlog_Model>) {
        items.clear()
        items.addAll(englishes)
    }


    interface OnItemClick {

        fun deleteTodo(cloths: Vlog_Model)

    }
}