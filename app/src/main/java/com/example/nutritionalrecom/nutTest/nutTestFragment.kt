package com.example.nutritionalrecom.nutTest

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.nutritionalrecom.databinding.FragmentNutTestBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class nutTestFragment : Fragment(), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var stepCountSensor: Sensor
    private lateinit var run_Spf: SharedPreferences
    private lateinit var reserve_Time_Spf: SharedPreferences

    private lateinit var currentSteps : String
    private lateinit var resved_Time : String



    private var _binding : FragmentNutTestBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutTestBinding.inflate(inflater, container, false)

        var now = System.currentTimeMillis()
        var date = Date(now)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var getTime = dateFormat.format(date)

        // 현재 시간을 가져오기
        val currentTimeString = getCurrentTime()
        val currentDate = dateFormat.parse(currentTimeString)

        run_Spf = requireActivity().getSharedPreferences("run_Spf", Context.MODE_PRIVATE)

        reserve_Time_Spf = requireActivity().getSharedPreferences("rsv_Time_Spf", Context.MODE_PRIVATE)

        resved_Time = reserve_Time_Spf.getString("reserve_Time_Count", getTime.toString()).toString()

        val run_Count : SharedPreferences.Editor = run_Spf.edit()
        val reserve_Time_Count : SharedPreferences.Editor = reserve_Time_Spf.edit()



        //하루지낫는지 확인


        // resved_Time을 Date 객체로 변환
        val reservedDate = dateFormat.parse(resved_Time)
        Log.d("하하","저장된 시간 : $reservedDate")

        // 자정 시간을 가져오기
        val calendar = Calendar.getInstance()
        calendar.time = reservedDate
        calendar.add(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        val adjustedReservedDate = calendar.time

 /*       Log.d("하하","자정 시간 : $adjustedReservedDate")*/


        // 현재 시간과 저장된 시간의 차이 계산 (밀리초 단위)
        val timeDifference = adjustedReservedDate.time - currentDate.time

        val remainingHours = timeDifference / (1000 * 60 * 60)
        val remainingMinutes = (timeDifference % (1000 * 60 * 60)) / (1000 * 60)
        val remainingSeconds = ((timeDifference % (1000 * 60 * 60)) % (1000 * 60)) / 1000

        binding.rsvCurrentCount.text = "자정까지 남은 시간 : $remainingHours 시간 $remainingMinutes 분 $remainingSeconds 초"

        Log.d("하하","남은 시간: $remainingHours 시간 $remainingMinutes 분 $remainingSeconds 초")


        GlobalScope.launch(Dispatchers.Main) {
            //currentSteps 즉 지금까지의 총 걸음수(TYPE_STEP_COUNTER)의 값을 받아오고 난후의 비동기처리후 빌드

            if(remainingHours < 0 || remainingMinutes < 0 || remainingSeconds < 0) {
                start_Vlog(currentSteps.toDouble() , run_Count, reserve_Time_Count)
                Toast.makeText(context, "현재 시간이 저장된 시각의 자정을 넘었습니다! 만보기를 초기화합니다!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "현재 시간이 아직 저장된 시각의 자정을 넘지 않았습니다.", Toast.LENGTH_SHORT).show()
            }
        }

        try {
            binding.stepCountView.text = (currentSteps.toDouble() - run_Spf.getString("run_Count", "달리세요!")!!.toDouble()).toString()
            binding.rsvCount.text = resved_Time
        } catch (e: Exception) {
            binding.stepCountView.text  = "달리세요!"
            binding.rsvCount.text = "등록된 시간"

        }

        // 활동 퍼미션 체크
        if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACTIVITY_RECOGNITION) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(arrayOf(Manifest.permission.ACTIVITY_RECOGNITION), 0)
        }

        // 걸음 센서 연결
        // * 옵션
        // - TYPE_STEP_DETECTOR:  리턴 값이 무조건 1, 앱이 종료되면 다시 0부터 시작
        // - TYPE_STEP_COUNTER : 앱 종료와 관계없이 계속 기존의 값을 가지고 있다가 1씩 증가한 값을 리턴
        //
        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)!!

        // 디바이스에 걸음 센서의 존재 여부 체크
        if (stepCountSensor == null) {
            Toast.makeText(requireActivity(), "No Step Sensor", Toast.LENGTH_SHORT).show()
        }


        binding.resetButton.setOnClickListener {
            try {
                start_Vlog(currentSteps.toDouble() , run_Count, reserve_Time_Count)
            }catch (e : Exception) {
                
            }
        }




        return binding.root
    }

    fun getCurrentTime(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(Date())
    }

    override fun onStart() {
        super.onStart()
        if (this::stepCountSensor.isInitialized) {
            sensorManager.registerListener(
                this,
                stepCountSensor,
                SensorManager.SENSOR_DELAY_FASTEST
            )
        }
    }

    override fun onSensorChanged(event: SensorEvent) {

        currentSteps = event.values[0].toString()

        if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {

            var test = event.values[0]

            Log.d("df","확인 $test")

            binding.stepCountView.text = (event.values[0].toString().toDouble() - run_Spf.getString("run_Count", 0.toString())!!.toDouble()).toString()
            binding.rsvCount.text = resved_Time
           /*reserve_Time_Count.putString("reserve_Time_Count", Calendar.getInstance().toString()).apply()*/
        }
    }

    fun start_Vlog(event: Double, run_Count: SharedPreferences.Editor, reserve_Time_Count: SharedPreferences.Editor) {
        var now = System.currentTimeMillis()
        var date = Date(now)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var getTime = dateFormat.format(date)

        Log.d("dfd","테스트 : $event")

        run_Count.putString("run_Count", event.toString()).apply()
        reserve_Time_Count.putString("reserve_Time_Count", getTime.toString()).apply()

        binding.stepCountView.text = 0.toString()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do nothing for now
    }



}