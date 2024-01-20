package com.example.nutritionalrecom.nutSleep

import android.app.*
import android.content.*
import android.content.Context.MODE_PRIVATE
import android.content.Context.NOTIFICATION_SERVICE
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentNutSleepBinding
import com.example.nutritionalrecom.databinding.FragmentNutTestBinding
import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.min

class nutSleepFragment : Fragment() {

    private var _binding : FragmentNutSleepBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var Sleepadapter: SleepAdapter

    val Sleep_List = arrayListOf<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutSleepBinding.inflate(inflater, container, false)

        binding.SleepRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
        }


        val Sleep_Hour: SharedPreferences = requireActivity().getSharedPreferences("Sleep_Hour", MODE_PRIVATE)
        val Hour_editor: SharedPreferences.Editor = Sleep_Hour.edit()

        val Sleep_Minute: SharedPreferences = requireActivity().getSharedPreferences("Sleep_Minute", MODE_PRIVATE)
        val Minute_editor: SharedPreferences.Editor = Sleep_Minute.edit()

        loadData(Sleep_Hour,Sleep_Minute)


        binding.buttonPickTime.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(
                requireActivity(),
                TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute ->
                    val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                    val sleepHours = String.format("%02d", hourOfDay)
                    val sleepMinutes = String.format("%02d", minute)

                    binding.textViewSelectedTime.text = "$sleepHours 시 $sleepMinutes 분"

                    // SharedPreferences에 데이터 저장
                    Hour_editor.putString("SleepHour", sleepHours).commit()
                    Minute_editor.putString("SleepMinute", sleepMinutes).commit()

                    Sleep_List.clear()
                    loadData(Sleep_Hour,Sleep_Minute)

                },
                hour,
                minute,
                true
            )

            timePickerDialog.show()
        }


        return binding.root
    }


    fun loadData(Sleep_Hour : SharedPreferences, Sleep_Minute : SharedPreferences) {

        try {
            var sleepHour = Sleep_Hour.getString("SleepHour", "Default Value")
            var sleepMinute = Sleep_Minute.getString("SleepMinute", "Default Value")
            binding.textViewSelectedTime.text = "$sleepHour 시 $sleepMinute 분"

            for (i in 0 until 7) {
                // 현재 값에 기반하여 새로운 시간 및 분 계산
              /*  val newHour = (sleepHour!!.toInt() + i) % 24 // 1시간 간격으로
                val newMinute = (sleepMinute!!.toInt() + i * 30) % 60 // 30분 간격으로

                // 시간을 형식화하고 Sleep_List에 추가
                val formattedTime = String.format("%02d:%02d", newHour, newMinute)

                Sleep_List.add(formattedTime)*/

                val currentTime = (sleepHour!!.toInt() * 60 + sleepMinute!!.toInt() + i * 90) % (24 * 60)

                // 시간과 분 계산
                val hour = currentTime / 60
                val minute = currentTime % 60

                // Sleep_List에 시간 추가
                if(i != 0) {
                    Sleep_List.add(String.format("%02d:%02d", hour, minute))
                }
            }


        } catch (e: Exception) {
            binding.textViewSelectedTime.text = "시간을 정해주세요!"
        }


        val Sleep_adapter = SleepAdapter(requireActivity(),Sleep_List)

        binding.SleepRecyclerView.adapter = Sleep_adapter
    }



}