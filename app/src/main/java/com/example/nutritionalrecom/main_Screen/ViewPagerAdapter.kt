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

    private var imglist = arrayListOf(R.drawable.cancer_image_image3,R.drawable.cancer_viewpager_image1, R.drawable.cancer_image_born2)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        var view = LayoutInflater.from(container.context).inflate(R.layout.pager, container,false)

        var screen_image = view.findViewById<ImageView>(R.id.main_Screen_Image)

        screen_image.setImageResource(imglist[position])
        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View?)
    }


    override fun getCount(): Int {
        return imglist.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return (view == `object`)
    }
}