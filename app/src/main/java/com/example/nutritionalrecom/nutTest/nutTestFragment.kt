package com.example.nutritionalrecom.nutTest

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import androidx.fragment.app.Fragment
import com.example.nutritionalrecom.R
import com.example.nutritionalrecom.databinding.FragmentNutTestBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.*
import kotlin.system.exitProcess


class nutTestFragment : Fragment(), SensorEventListener {

    val fire_Db = FirebaseFirestore.getInstance()    // Firestore 인스턴스 선언

    private lateinit var sensorManager: SensorManager
    private lateinit var stepCountSensor: Sensor
    private lateinit var run_Spf: SharedPreferences
    private lateinit var reserve_Time_Spf: SharedPreferences

    private var currentSteps : Int = 0
    private lateinit var resved_Time : String

    private val IMAGE_PICK = 1111

    lateinit var storage: FirebaseStorage

    private var _binding : FragmentNutTestBinding?= null    // 뷰 바인딩
    private val binding get() = _binding!!

    var selectImage: Uri?=null

    private lateinit var select_RunImage: ImageView

    lateinit var run_Count: SharedPreferences.Editor
    lateinit var reserve_Time_Count: SharedPreferences.Editor

    var remainingHours: Long = 0
    var remainingMinutes: Long = 0
    var remainingSeconds: Long = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNutTestBinding.inflate(inflater, container, false)

        storage = FirebaseStorage.getInstance()

        var now = System.currentTimeMillis()
        var date = Date(now)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var getTime = dateFormat.format(date)

        // 현재 시간을 가져오기
        val currentTimeString = getCurrentTime()
        val currentDate = dateFormat.parse(currentTimeString)

        val doc = UUID.randomUUID().toString()

        run_Spf = requireActivity().getSharedPreferences("run_Spf", Context.MODE_PRIVATE)

        reserve_Time_Spf = requireActivity().getSharedPreferences("rsv_Time_Spf", Context.MODE_PRIVATE)

        resved_Time = reserve_Time_Spf.getString("reserve_Time_Count", getTime.toString()).toString()

        run_Count = run_Spf.edit()
        reserve_Time_Count = reserve_Time_Spf.edit()


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

        remainingHours = timeDifference / (1000 * 60 * 60)
        remainingMinutes = (timeDifference % (1000 * 60 * 60)) / (1000 * 60)
        remainingSeconds = ((timeDifference % (1000 * 60 * 60)) % (1000 * 60)) / 1000

        binding.rsvCurrentCount.text = "$remainingHours 시간 $remainingMinutes 분 $remainingSeconds 초"

        Log.d("하하","남은 시간: $remainingHours 시간 $remainingMinutes 분 $remainingSeconds 초")


        GlobalScope.launch(Dispatchers.Main) {
            //currentSteps 즉 지금까지의 총 걸음수(TYPE_STEP_COUNTER)의 값을 받아오고 난후의 비동기처리후 빌드

            if(remainingHours < 0 || remainingMinutes < 0 || remainingSeconds < 0) {
                run_Count.putInt("run_Count", 0).apply()
                binding.stepCountView.text = 0.toString()
                Toast.makeText(context, "자정이 지낫습니다! Start버튼을 눌러주세요!", Toast.LENGTH_SHORT).show()
            } else {

            }

        }

        try {
            if(run_Spf.getInt("run_Count", 0)!!.toInt() == 0) {
                binding.stepCountView.text = 0.toString()
            }else {
                binding.stepCountView.text = ((currentSteps - run_Spf.getInt("run_Count", 0)!!.toInt()).toString())

                GlobalScope.launch(Dispatchers.Main) {

                    binding.currentKcal.text = "소모한 칼로리 : " + DecimalFormat("#.###").format(
                        (((currentSteps - run_Spf.getInt("run_Count", 0)!!.toInt()).toString().toInt()) * 0.04)
                    ) + " Kcal"

                    binding.currentKm.text = "걸은 거리 : " + DecimalFormat("#.###").format(
                        ((((currentSteps - run_Spf.getInt("run_Count", 0)!!.toInt()).toString().toInt()) * 0.75)
                    ) * 0.001.toFloat()) + " Km"

                }
            }
        } catch (e: Exception) {
            binding.stepCountView.text  = "달리세요!"
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
            val builder = AlertDialog.Builder(requireActivity())
            val tvName = TextView(requireActivity())
            tvName.text = "달리기를 시작하시겟습니까?"

            val mLayout = LinearLayout(requireActivity())
            mLayout.orientation = LinearLayout.VERTICAL
            mLayout.setPadding(16)
            mLayout.addView(tvName)
            builder.setView(mLayout)

            builder.setPositiveButton("확인") { dialog, which ->
                start_Vlog(currentSteps , run_Count, reserve_Time_Count)
            }
            builder.setNegativeButton("취소") { dialog, which ->
            }
            builder.show()
        }

        binding.runCheck.setOnClickListener {
            if(run_Spf.getInt("run_Count", 0)!!.toInt() == 0) {

                Toast.makeText(context, "먼저 시작 버튼을 눌러주셔야 기록이 가능합니다!", Toast.LENGTH_SHORT).show()

            }else {

                val builder = AlertDialog.Builder(requireActivity())
                val run_Nickname = EditText(requireActivity())
                run_Nickname.hint = "닉네임을 입력하세요!"


                select_RunImage = ImageView(requireActivity())
                select_RunImage.setImageResource(R.drawable.baseline_photo_24)

                // 이미지 크기 조정
                val layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, // 원하는 너비 (MATCH_PARENT, WRAP_CONTENT 등)
                    300
                )

                select_RunImage.layoutParams = layoutParams

                val mLayout = LinearLayout(requireActivity())
                mLayout.orientation = LinearLayout.VERTICAL
                mLayout.setPadding(16)
                mLayout.addView(run_Nickname)
                mLayout.addView(select_RunImage)
                builder.setView(mLayout)

                select_RunImage.setOnClickListener {
                    var intent = Intent(Intent.ACTION_PICK) //선택하면 무언가를 띄움. 묵시적 호출
                    intent.type = "image/*"
                    startActivityForResult(intent, IMAGE_PICK)
                }

                builder.setTitle("기록 갱신")
                builder.setPositiveButton("등록") { dialog, which ->

                    var now = System.currentTimeMillis()
                    var date = Date(now)

                    var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

                    // 자정 시간을 가져오기
                    val calendar = Calendar.getInstance()
                    calendar.time = reservedDate
                    calendar.add(Calendar.DAY_OF_MONTH, 1)
                    calendar.set(Calendar.HOUR_OF_DAY, 0)
                    calendar.set(Calendar.MINUTE, 0)
                    calendar.set(Calendar.SECOND, 0)
                    val adjustedReservedDate = calendar.time

                    // 현재 시간을 가져오기
                    val currentTimeString = getCurrentTime()
                    val currentDate = dateFormat.parse(currentTimeString)


                    val timeDifference = adjustedReservedDate.time - currentDate.time

                    remainingHours = timeDifference / (1000 * 60 * 60)
                    remainingMinutes = (timeDifference % (1000 * 60 * 60)) / (1000 * 60)
                    remainingSeconds = ((timeDifference % (1000 * 60 * 60)) % (1000 * 60)) / 1000

                    if(remainingHours < 0 || remainingMinutes < 0 || remainingSeconds < 0) {
                        Toast.makeText(context, "자정이 지나 랭킹을 등록하실수없습니다..", Toast.LENGTH_SHORT).show()
                    } else {
                        if (selectImage != null) {
                            if (run_Nickname.text.toString().trim { it <= ' ' }.isEmpty()) {
                                Toast.makeText(requireActivity(), "닉네임을 입력하세요", Toast.LENGTH_SHORT).show()
                            } else {
                                var fileName =
                                    SimpleDateFormat("yyyyMMddHHmmss").format(Date()) // 파일명이 겹치면 안되기 떄문에 시년월일분초 지정
                                storage.reference.child("Ranking_image").child(fileName)
                                    .putFile(selectImage!!)//어디에 업로드할지 지정
                                    .addOnSuccessListener { taskSnapshot -> // 업로드 정보를 담는다
                                        taskSnapshot.metadata?.reference?.downloadUrl?.addOnSuccessListener { it ->
                                            //var imageUrl=it.toString()
                                            //var photo= Photo(textEt.text.toString(),imageUrl)

                                            val ranking_Data = hashMapOf(
                                                "NickName" to run_Nickname.text.toString(),
                                                "Ranking_Doc" to doc,
                                                "Run_Count" to binding.stepCountView.text.toString().toInt(),
                                                "ranking_Image" to it.toString()
                                            )

                                            fire_Db.collection("Ranking").document(doc)
                                                .set(ranking_Data)
                                                .addOnSuccessListener {
                                                    Toast.makeText(requireActivity(), "랭킹에 등록되었습니다!", Toast.LENGTH_SHORT).show()
                                                }
                                                .addOnFailureListener { e ->
                                                    Toast.makeText(requireActivity(), "${e}", Toast.LENGTH_SHORT).show()
                                                    //여기
                                                    Log.d("에러" ,e.toString())
                                                }


                                        }
                                    }
                            }
                        } else {
                            if (run_Nickname.text.toString().trim  { it <= ' ' }.isEmpty()) {
                                Toast.makeText(requireActivity(), "입력하세요", Toast.LENGTH_SHORT).show()
                            } else {
                                    val ranking_Data = hashMapOf(
                                        "NickName" to run_Nickname.text.toString(),
                                        "Ranking_Doc" to doc,
                                        "Run_Count" to binding.stepCountView.text.toString().toInt(),
                                        "ranking_Image" to "https://cdn.pixabay.com/photo/2017/02/03/05/32/man-2034538_1280.png"
                                    )

                                    fire_Db.collection("Ranking").document(doc)
                                        .set(ranking_Data)
                                        .addOnSuccessListener {
                                            Toast.makeText(
                                                requireActivity(),
                                                "랭킹에 등록되었습니다!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(
                                                requireActivity(),
                                                "${e}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            Log.d("에러" ,e.toString())
                                        }


                                }
                            }
                        }
                    }
                builder.show()
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

        currentSteps = event.values[0].toInt()

        if (event.sensor.type == Sensor.TYPE_STEP_COUNTER) {

            var test = event.values[0]

            Log.d("df","확인 $test")

            if(run_Spf.getInt("run_Count", 0)!!.toInt() == 0) {
                binding.stepCountView.text = 0.toString()
            }else {
                binding.stepCountView.text = (event.values[0].toInt() - run_Spf.getInt("run_Count", 0)).toString()
                binding.currentKcal.text = "소모한 칼로리 : " + DecimalFormat("#.###").format(
                    (((currentSteps - run_Spf.getInt("run_Count", 0)!!.toInt()).toString().toInt()) * 0.04)
                ) + " Kcal"
                binding.currentKm.text = "걸은 거리 : " + DecimalFormat("#.###").format(
                    ((((currentSteps - run_Spf.getInt("run_Count", 0)!!.toInt()).toString().toInt()) * 0.75)
                ) * 0.001.toFloat()) + " km"
                if(remainingHours < 0 || remainingMinutes < 0 || remainingSeconds < 0) {
                    run_Count.putInt("run_Count", 0).apply()
                    binding.currentKcal.text = "소모한 칼로리 : 0"
                    Toast.makeText(context, "자정이 지낫습니다! 만보기를 초기화합니다!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun start_Vlog(event: Int, run_Count: SharedPreferences.Editor, reserve_Time_Count: SharedPreferences.Editor) {
        var now = System.currentTimeMillis()
        var date = Date(now)

        var dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        var getTime = dateFormat.format(date)

        Log.d("dfd","테스트 : $event")

        run_Count.putInt("run_Count", event).apply()
        reserve_Time_Count.putString("reserve_Time_Count", getTime.toString()).apply()

        binding.stepCountView.text = 0.toString()
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Do nothing for now
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            selectImage = data?.data
            select_RunImage.setImageURI(selectImage)
        }
    }



}