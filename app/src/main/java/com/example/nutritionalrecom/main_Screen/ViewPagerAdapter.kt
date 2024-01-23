package com.example.nutritionalrecom.main_Screen

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import com.example.nutritionalrecom.R

class ViewPagerAdapter : PagerAdapter() {

    ///첫번째페이지 뷰페이져a

    private var context1 : Context?= null

    private var imglist = arrayListOf<Int>(R.drawable.cancer_image_image3,R.drawable.cancer_viewpager_image1, R.drawable.cancer_image_born2)

    @SuppressLint("MissingInflatedId")
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(container.context).inflate(R.layout.pager, container,false)

        var screen_image = view.findViewById<ImageView>(R.id.main_Screen_Image)

        var item = imglist
        screen_image.setImageResource(item[position])
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


    override fun getCount(): Int {
        return 3
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }
}