package com.example.nutritionalrecom.nutSleep

import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.NOTIFICATION_SERVICE
import android.content.ContextWrapper
import android.content.Intent
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
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentNutSleepBinding
import com.example.nutritionalrecom.databinding.FragmentNutTestBinding
import java.util.*

class nutSleepFragment : Fragment() {

    private var _binding : FragmentNutSleepBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    private val CHANNEL_ID = "testChannel01"   // Channel for notification
    private var notificationManager: NotificationManager? = null

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

        binding.startButton.setOnClickListener {
            Toast.makeText(requireActivity(), "으어어", Toast.LENGTH_SHORT).show()
            displayNotification()
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