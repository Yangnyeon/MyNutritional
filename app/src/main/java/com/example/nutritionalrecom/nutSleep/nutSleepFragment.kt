package com.example.nutritionalrecom.nutSleep

import android.app.*
import android.content.*
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
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentNutSleepBinding
import com.example.nutritionalrecom.databinding.FragmentNutTestBinding
import java.util.*
import kotlin.collections.ArrayList

class nutSleepFragment : Fragment() {

    private var _binding : FragmentNutSleepBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var Sleepadapter: SleepAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutSleepBinding.inflate(inflater, container, false)

        sharedPreferences = requireActivity().getSharedPreferences("MySleepClock", Context.MODE_PRIVATE)

        createNotificationChannel(CHANNEL_ID, "testChannel", "this is a test Channel")


        binding.SleepRecyclerView.apply {
                layoutManager = LinearLayoutManager(requireActivity())
        }

        val Sleep_List = arrayListOf("Sleep Item 1", "Sleep Item 2", "Sleep Item 3")

        val Sleep_adapter = SleepAdapter(requireActivity(),Sleep_List)

        binding.SleepRecyclerView.adapter = Sleep_adapter



        //자는시간 불러오기
        loadData()

        binding.startButton.setOnClickListener {
            Toast.makeText(requireActivity(), "으어어", Toast.LENGTH_SHORT).show()
            displayNotification()
        }

        binding.buttonPickTime.setOnClickListener {
            showTimePickerDialog()
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

    //시간
    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireActivity(),
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                val selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                binding.textViewSelectedTime.text = "$selectedTime"
                saveData()
            },
            hour,
            minute,
            true
        )

        timePickerDialog.show()
    }

    //수면시간 기록
    private fun saveData() {
        val SleepValue =  binding.textViewSelectedTime.text.toString()

        // SharedPreferences에 데이터 저장
        val editor = sharedPreferences.edit()
        editor.putString("SleepValue", SleepValue)
        editor.apply()

        binding.textViewSelectedTime.text = "$SleepValue"
    }

    private fun loadData() {
        // SharedPreferences에서 데이터 불러오기
        val savedValue = sharedPreferences.getString("SleepValue", "Default Value")

        binding.textViewSelectedTime.text = "$savedValue"
    }

    private fun getData(): ArrayList<String> {
        // 간단한 문자열 목록 반환
        return arrayListOf("Item 1", "Item 2", "Item 3", "Item 4", "Item 5")
    }

}