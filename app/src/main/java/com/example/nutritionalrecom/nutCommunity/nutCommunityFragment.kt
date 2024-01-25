package com.example.nutritionalrecom.nutCommunity

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager
import com.example.nutritionalrecom.databinding.FragmentNutCommunityBinding
import kotlinx.coroutines.Dispatchers
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

        binding.enrollCloths.setOnClickListener {
            val memo_dialog = check_VLOG(requireActivity(),this)

            memo_dialog.show()
        }

        val mAdapter = vlog_Adapter(this)
        binding.recyclerViewTodo.adapter = mAdapter
        binding.recyclerViewTodo.layoutManager = LinearLayoutManager(requireActivity())

        val repository = vlog_Repository(requireActivity().application)
        val viewModelFactory = vlog_Factory(repository)

        vlog_ViewModel_var = ViewModelProvider(this, viewModelFactory)[vlog_ViewModel::class.java]

      /*  vlog_ViewModel_var.readDateData(Integer.parseInt(getYear),Integer.parseInt(getMonth + 1),Integer.parseInt(getDay)).observe(requireActivity(), androidx.lifecycle.Observer {
            mAdapter.setList(it)
            mAdapter.notifyDataSetChanged()
        })*/

        vlog_ViewModel_var.getAll().observe(requireActivity(), {
            mAdapter.setList(it)
            mAdapter.notifyDataSetChanged()
        })




        vlog_ViewModel_var.readDateData(Integer.parseInt(getYear),Integer.parseInt(getMonth),Integer.parseInt(getDay)).observe(requireActivity(),{
            mAdapter.setList(it)
            mAdapter.notifyDataSetChanged()
        })

        binding.calendarView.selectionManager = SingleSelectionManager(OnDaySelectedListener {
            val days: List<Calendar> = binding.calendarView.getSelectedDates()
            var result = ""
            for (i in days.indices) {
                /*
                val week: String = SimpleDateFormat("EE").format(calendar.time)
                val day_full =
                    year.toString() + "년 " + (month + 1) + "월 " + day + "일 " + week + "요일"
                result +=  "$day_full"

                 */
                val calendar = days[i]
                val day = calendar[Calendar.DAY_OF_MONTH]
                val month = calendar[Calendar.MONTH]
                val year = calendar[Calendar.YEAR]

                binding.calendarDateText.text = "${year}/${month + 1}/${day}"

                vlog_ViewModel_var.readDateData(year,month + 1,day).observe(requireActivity(), {
                    mAdapter.setList(it)
                    mAdapter.notifyDataSetChanged()
                })

            }
        })








        return binding.root
    }

    override fun deleteTodo(vlog: Vlog_Model) {
        vlog_ViewModel_var.delete(vlog)
    }

    override fun check_memo(content: String, dialog: Dialog) {
            val currentTime : Long = System.currentTimeMillis()

            //val year = SimpleDateFormat("yyyy-MM-dd k:mm:ss")
            val year = SimpleDateFormat("yyyy")
            val month = SimpleDateFormat("MM")
            val day = SimpleDateFormat("dd")
            val time = SimpleDateFormat("k:mm:ss")

            val mDate: Date = Date(currentTime)

            val getYear = year.format(mDate)
            val getMonth = month.format(mDate)
            val getDay = day.format(mDate)
            val gettime = time.format(mDate)


            vlog_ViewModel_var.insert(Vlog_Model(content, Integer.parseInt(getYear), Integer.parseInt(getMonth), Integer.parseInt(getDay), gettime.toString()))
            Toast.makeText(requireActivity(),"오늘일기가 저장되었습니다...", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
    }

}