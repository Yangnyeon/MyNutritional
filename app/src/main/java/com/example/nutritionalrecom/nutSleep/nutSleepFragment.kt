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


        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")


        binding.SleepRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
        }

        binding.startButton.setOnClickListener {
            Toast.makeText(requireActivity(), "으어어", Toast.LENGTH_SHORT).show()
            displayNotification()
        }

        val Sleep_Hour: SharedPreferences = requireActivity().getSharedPreferences("Sleep_Hour", MODE_PRIVATE)
        val Hour_editor: SharedPreferences.Editor = Sleep_Hour.edit()

        val Sleep_Minute: SharedPreferences = requireActivity().getSharedPreferences("Sleep_Minute", MODE_PRIVATE)
        val Minute_editor: SharedPreferences.Editor = Sleep_Minute.edit()

        Sleep_List.clear()

        try {
            var sleepHour = Sleep_Hour.getString("SleepHour", "Default Value")
            var sleepMinute = Sleep_Minute.getString("SleepMinute", "Default Value")
            binding.textViewSelectedTime.text = "$sleepHour : $sleepMinute"

            for (i in 0 until 6) {
                // 현재 값에 기반하여 새로운 시간 및 분 계산
                val newHour = (sleepHour!!.toInt() + i) % 24 // 2시간 간격으로
                val newMinute = (sleepMinute!!.toInt() + i * 30) % 60 // 30분 간격으로

                // 시간을 형식화하고 Sleep_List에 추가
                val formattedTime = String.format("%02d:%02d", newHour, newMinute)
                if(i != 0) {
                    Sleep_List.add(formattedTime)
                }
            }


        } catch (e: Exception) {
            binding.textViewSelectedTime.text = "시간을 정해주세요!"
        }

        /* val Sleep_List = arrayListOf(savedValue + 1, "Sleep Item 2", "Sleep Item 3")*/

        val Sleep_adapter = SleepAdapter(requireActivity(),Sleep_List)

        binding.SleepRecyclerView.adapter = Sleep_adapter

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

                    binding.textViewSelectedTime.text = selectedTime

                    // SharedPreferences에 데이터 저장
                    Hour_editor.putString("SleepHour", sleepHours).commit()
                    Minute_editor.putString("SleepMinute", sleepMinutes).commit()

                },
                hour,
                minute,
                true
            )

            timePickerDialog.show()
        }


        return binding.root
    }
    private fun displayNotification() {
        val notificationId = 45

        val notification = Notification.Builder(requireActivity(), CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_accessibility_24)
            .setContentTitle("Example")
            .setContentText("This is Notification Test")
            .setDefaults(Notification.DEFAULT_ALL)
            .setAutoCancel(true)
            .build()

        notificationManager?.notify(notificationId, notification)
    }

    private fun createNotificationChannel(channelId: String, name: String, channelDescription: String) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val importance = NotificationManager.IMPORTANCE_DEFAULT // set importance
            val channel = NotificationChannel(channelId, name, importance).apply {
                description = channelDescription
            }
            // Register the channel with the system
            notificationManager = requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager?.createNotificationChannel(channel)
        }

    }

}