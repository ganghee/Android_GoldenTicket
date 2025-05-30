package com.dazzi.goldenticket.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.fragment.TutorialSliderFragment

class TutorialSliderAdapter(fm: FragmentManager, private val number: Int) : FragmentStatePagerAdapter(
    fm,
    BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT
) {


    override fun getItem(p0: Int): Fragment {

        //보여질 fragment들은 모두 같은 클래스 상속
        val fragment = TutorialSliderFragment()

        //한 개 bundle에 담길 데이터의 개수 지정
        val bundle = Bundle(1)

        //Fragment별 배경이미지 지정
        when (p0) {
            0 -> bundle.putInt(
                "tutorial_img",
                R.drawable.img_tutorial01
            )
            1 -> bundle.putInt(
                "tutorial_img",
                R.drawable.img_tutorial02
            )
            2 -> bundle.putInt(
                "tutorial_img",
                R.drawable.img_tutorial03
            )
        }
        //Bundle 객체를 arguments에 전달
        fragment.arguments = bundle
        return fragment
    }

    override fun getCount(): Int {
        return number
    }
}