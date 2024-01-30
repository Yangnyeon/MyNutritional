package com.example.nutritionalrecom

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.core.view.GravityCompat
import com.example.nutritionalrecom.databinding.ActivityMainBinding
import com.example.nutritionalrecom.diet_Food.diet_Food_Fragment
import com.example.nutritionalrecom.main_Screen.Main_Screen
import com.example.nutritionalrecom.nutCommunity.nutCommunityFragment
import com.example.nutritionalrecom.nutSleep.nutSleepFragment
import com.example.nutritionalrecom.nutTest.nutTestFragment
import com.example.nutritionalrecom.nutTypes.nutTypesFragment
import com.example.nutritionalrecom.ranking_Fagment.ranking_Fragment
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import java.io.File

class MainActivity : AppCompatActivity() {

    private var mBinding: ActivityMainBinding? = null
    private val binding get() = mBinding!!

    val fragment = Main_Screen()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val dexOutputDir: File = codeCacheDir
        dexOutputDir.setReadOnly()
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
                    val MainFragment3 = ranking_Fragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment3).commit()
                }
                R.id.third -> {
                    val MainFragment4 = nutSleepFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment4).commit()
                }
            }
        }

        setContentView(binding.root)
    }

    fun onFragmentChanged(index: Int) {

        if (index == 0) {
            val MainFragment_nut = nutTypesFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, MainFragment_nut).commitAllowingStateLoss()
            binding.bottonavi.setItemSelected(R.id.first)
        }

        if (index == 1) {
            val diet_Food_Fragment = diet_Food_Fragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, diet_Food_Fragment).commitAllowingStateLoss()
            binding.bottonavi.setItemSelected(R.id.first)
        }

        if (index == 2) {
            val run_Fragment = nutTestFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, run_Fragment).commitAllowingStateLoss()
            binding.bottonavi.setItemSelected(R.id.first)
        }

        if (index == 3) {
            val today_Food = nutCommunityFragment()
            supportFragmentManager.beginTransaction().replace(R.id.container, today_Food).commitAllowingStateLoss()
            binding.bottonavi.setItemSelected(R.id.first)
        }

    }
}