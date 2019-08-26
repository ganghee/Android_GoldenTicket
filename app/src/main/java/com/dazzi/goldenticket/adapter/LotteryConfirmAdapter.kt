package com.dazzi.goldenticket.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter
import com.dazzi.goldenticket.fragment.LotteryFirstTimerFragment
import com.dazzi.goldenticket.fragment.LotterySecondTimerFragment

class LotteryConfirmAdapter(fm: FragmentManager, private val num_fragment: Int) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> LotteryFirstTimerFragment()
            1 -> LotterySecondTimerFragment()
            else -> null
        }!!
    }

    override fun getCount() = num_fragment

    override fun getItemPosition(`object`: Any) = PagerAdapter.POSITION_NONE

}

