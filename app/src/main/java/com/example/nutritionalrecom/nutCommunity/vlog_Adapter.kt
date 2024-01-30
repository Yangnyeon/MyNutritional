package com.example.nutritionalrecom.nutCommunity

import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.VlotItemBinding
import java.text.SimpleDateFormat
import java.util.*

class vlog_Adapter (listener: OnItemClick,var context : Context) : RecyclerView.Adapter<vlog_Adapter.TodoViewHolder>() {

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

            binding.todayDelete.setOnClickListener {
               val alertDialogBuilder = AlertDialog.Builder(context)

                // AlertDialog 제목 및 메시지 설정
                alertDialogBuilder.setTitle("삭제하시겟습니까?")

                // 확인 버튼 설정 및 클릭 리스너
                alertDialogBuilder.setPositiveButton("확인") { dialog: DialogInterface, _: Int ->
                    // 확인 버튼이 눌렸을 때 실행되는 코드

                    mCallback.deleteTodo(cloths)
                    Toast.makeText(context, "삭제되었습니다!", Toast.LENGTH_SHORT).show()
                    dialog.dismiss() // 다이얼로그 닫기
                }

                // 취소 버튼 설정 및 클릭 리스너
                alertDialogBuilder.setNegativeButton("취소") { dialog: DialogInterface, _: Int ->
                    // 취소 버튼이 눌렸을 때 실행되는 코드

                    dialog.dismiss() // 다이얼로그 닫기
                }.create()

                alertDialogBuilder.show()

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