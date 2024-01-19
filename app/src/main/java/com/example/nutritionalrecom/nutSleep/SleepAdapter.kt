package com.example.nutritionalrecom.nutSleep

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionalrecom.R

class SleepAdapter(val context: Context,val itemList: ArrayList<String>) : RecyclerView.Adapter<SleepAdapter.ViewHolder>() {

    // 뷰홀더 생성
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.sleep_layout, parent, false)
        return ViewHolder(view)
    }

    // 데이터를 뷰홀더에 바인딩
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        /*holder.notice_name.text = itemList[position].Sleep_time*/
        holder.notice_name.text = itemList[position]
    }

    // 데이터 아이템의 개수 반환
    override fun getItemCount(): Int {
        return itemList.size
    }

    // 뷰홀더 클래스
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val notice_name: TextView = itemView.findViewById(R.id.notice_name)
        val notice_date : TextView = itemView.findViewById(R.id.notice_date)

    }
}