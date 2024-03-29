package com.example.nutritionalrecom.nutTypes

import android.app.Application
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FoodHolderBinding
import com.example.nutritionalrecom.nutCommunity.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class food_Adapter(var items: MutableList<Items>? = ArrayList<Items>(), val context: Context,var owner : ViewModelStoreOwner,var application: Application) : RecyclerView.Adapter<food_Adapter.MyViewHolder>()
, OnItemClick{


    private lateinit var vlog_ViewModel_var : vlog_ViewModel

    private lateinit var memo_dialog : check_VLOG

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): food_Adapter.MyViewHolder {
        val binding = FoodHolderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: food_Adapter.MyViewHolder, position: Int) {
        holder.bind((items?.get(position) ?: 0) as Items)

        val randomColor = getRandomColor()

        // 배경색을 랜덤 색상으로 설정
        holder.food_LinearLayout.setBackgroundColor(randomColor)

        val alertDialogBuilder = AlertDialog.Builder(context)

        // AlertDialog 제목 및 메시지 설정
        alertDialogBuilder.setTitle("알림")
        alertDialogBuilder.setMessage("오늘 먹은 음식을 등록하시겟습니까?")

        // 확인 버튼 설정 및 클릭 리스너
        alertDialogBuilder.setPositiveButton("확인") { dialog: DialogInterface, _: Int ->
            // 확인 버튼이 눌렸을 때 실행되는 코드

            val currentTime : Long = System.currentTimeMillis()

            //val year = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
            val year = SimpleDateFormat("yyyy")
            val month = SimpleDateFormat("MM")
            val eng_month = SimpleDateFormat("MMM", Locale.ENGLISH)
            val day = SimpleDateFormat("dd")
            val time = SimpleDateFormat("k:mm:ss")

            val mDate: Date = Date(currentTime)

            val getYear = year.format(mDate)
            val getMonth = month.format(mDate)
            val geteng_Month = eng_month.format(mDate)
            val getDay = day.format(mDate)
            val gettime = time.format(mDate)
            val getKcal = items!![position].NUTR_CONT1

            val repository = vlog_Repository(application)
            val viewModelFactory = vlog_Factory(repository)

            vlog_ViewModel_var = ViewModelProvider(owner ,viewModelFactory)[vlog_ViewModel::class.java]

            vlog_ViewModel_var.insert(Vlog_Model(items?.get(position)?.DESC_KOR.toString(), Integer.parseInt(getYear), Integer.parseInt(getMonth),Integer.parseInt(getDay),geteng_Month.toString(),getKcal.toString(),gettime.toString()))

            Toast.makeText(context, "오늘 먹은음식이 등록되었습니다!", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        // 취소 버튼 설정 및 클릭 리스너
        alertDialogBuilder.setNegativeButton("취소") { dialog: DialogInterface, _: Int ->
            dialog.dismiss()
        }

        // 다이얼로그 보이기
        val alertDialog = alertDialogBuilder.create()

        holder.food_Recoder.setOnClickListener {
            alertDialog.show()
        }

        holder.itemView.setOnClickListener {
            memo_dialog = check_VLOG(context,this,items!![position].DESC_KOR.toString(),items!![position].SERVING_WT.toString()
                ,items!![position].NUTR_CONT1.toString(),items!![position].NUTR_CONT3.toString(), items!![position].NUTR_CONT5.toString(),items!![position].NUTR_CONT7.toString())
            memo_dialog.show()
        }

    }


    override fun getItemCount(): Int {
        return items?.size ?: 0
    }


    inner class MyViewHolder(private val binding: FoodHolderBinding) : RecyclerView.ViewHolder(binding.root) {
        //위의 onCreateViewHolder에서 생성된 view를 가지고 실행함

        val food_LinearLayout: RelativeLayout = itemView.findViewById(R.id.food_DataBackGround)
        var food_Recoder : ImageView = itemView.findViewById(R.id.food_Record)

       fun bind(item: Items) {

            binding.fooddata = item

        }



    }

    // 랜덤 색상을 생성하는 함수
    private fun getRandomColor(): Int {
        val brightColors = arrayOf(
            Color.rgb(255, 165, 0),  // 밝은 주황색
            Color.rgb(144, 238, 144), // 밝은 녹색
            Color.rgb(255, 218, 185), // 밝은 살색
            Color.rgb(255, 182, 193), // 밝은 핑크색
            Color.rgb(255, 255, 102)  // 밝은 노란색
        )

        // brightColors 배열에서 랜덤으로 하나를 선택
        return brightColors.random()
    }

    override fun deleteTodo(vlog: Vlog_Model) {

    }

    override fun check_memo(dialog: Dialog) {
        dialog.dismiss()
    }


}