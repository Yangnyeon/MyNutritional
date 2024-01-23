package com.example.nutritionalrecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.nutritionalrecom.databinding.ActivityMainBinding
import com.example.nutritionalrecom.main_Screen.Main_Screen
import com.example.nutritionalrecom.nutCommunity.nutCommunityFragment
import com.example.nutritionalrecom.nutSleep.nutSleepFragment
import com.example.nutritionalrecom.nutTest.nutTestFragment
import com.example.nutritionalrecom.nutTypes.nutTypesFragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    val fragment = Main_Screen()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*setContentView(R.layout.activity_main)*/

        mBinding = ActivityMainBinding.inflate(layoutInflater)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.commit()

        mBinding!!.bottonavi.setItemSelected(R.id.first)

        mBinding!!.bottonavi.setOnItemSelectedListener {
            when (it) {
                R.id.first -> {
                   /* val MainFragment1 = nutTypesFragment()*/
                    val MainFragment1 = Main_Screen()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment1).commit()
                }
                R.id.second -> {
                    val MainFragment2 = nutCommunityFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment2).commit()
                }
                R.id.third -> {
                    val MainFragment3 = nutTestFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment3).commit()
                }
                R.id.four -> {
                    val MainFragment4 = nutSleepFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment4).commit()
                }
            }
        }

        setContentView(binding.root)
    }
}