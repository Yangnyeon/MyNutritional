package com.example.nutritionalrecom.nutCommunity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.applikeysolutions.cosmocalendar.settings.lists.connected_days.ConnectedDays
import com.example.nutritionalrecom.MainActivity
import com.example.nutritionalrecom.databinding.FragmentNutCommunityBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class nutCommunityFragment : Fragment(), OnItemClick, vlog_Adapter.OnItemClick {

    private var _binding : FragmentNutCommunityBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    private lateinit var vlog_ViewModel_var : vlog_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutCommunityBinding.inflate(inflater, container, false)

        val currentTime : Long = System.currentTimeMillis()

        val year = SimpleDateFormat("yyyy")
        val month = SimpleDateFormat("MM")
        val day = SimpleDateFormat("dd")
        val time = SimpleDateFormat("k:mm:ss")

        val mDate: Date = Date(currentTime)
        val getYear = year.format(mDate)
        val getMonth = month.format(mDate)
        val getDay = day.format(mDate)

        val mAdapter = vlog_Adapter(this,requireActivity())
        binding.recyclerViewTodo.adapter = mAdapter
        binding.recyclerViewTodo.layoutManager = LinearLayoutManager(requireActivity())

        val repository = vlog_Repository(requireActivity().application)

        val viewModelFactory = vlog_Factory(repository)
        vlog_ViewModel_var = ViewModelProvider(this, viewModelFactory)[vlog_ViewModel::class.java]


        vlog_ViewModel_var.readDateData(Integer.parseInt(getYear),Integer.parseInt(getMonth),Integer.parseInt(getDay)).observe(requireActivity()) {
            val today_KcalSum = it.sumByDouble {
                it.kcal.toDouble()
            }
            mAdapter.setList(it)
            mAdapter.notifyDataSetChanged()
            binding.todayKcal.text = "오늘먹은 칼로리 : $today_KcalSum kcal"
            Log.d("지금시간확인 연도 : ", Integer.parseInt(getYear).toString())
            Log.d("지금시간확인 월 : ", Integer.parseInt(getMonth).toString())
            Log.d("지금시간확인 일 : ", Integer.parseInt(getDay).toString())
        }

        binding.calendarView.selectionManager = SingleSelectionManager(OnDaySelectedListener {
            val days: List<Calendar> = binding.calendarView.selectedDates


            for (i in days.indices) {

                val calendar = days[i]
                val day = calendar[Calendar.DAY_OF_MONTH]
                val month = calendar[Calendar.MONTH]
                val year = calendar[Calendar.YEAR]

                vlog_ViewModel_var.readDateData(year,month + 1,day).observe(requireActivity()) {
                    val today_KcalSum = it.sumByDouble {
                        it.kcal.toDouble()
                    }
                    mAdapter.setList(it)
                    mAdapter.notifyDataSetChanged()
                    binding.todayKcal.text = "오늘먹은 칼로리 : $today_KcalSum kcal"
                }

            }


        })

        var intent = Intent(requireActivity(), MainActivity::class.java)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {

            startActivity(intent)
        }



        return binding.root
    }

    override fun deleteTodo(vlog: Vlog_Model) {
        vlog_ViewModel_var.delete(vlog)
    }

    override fun check_memo(dialog: Dialog) {
        TODO("Not yet implemented")
    }


}