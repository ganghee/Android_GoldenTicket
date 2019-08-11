package com.dazzi.goldenticket.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dazzi.goldenticket.fragment.MainAllFragment
import com.dazzi.goldenticket.fragment.MainTodayFragment

class MainPagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val title = arrayOf("Today", "All")

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> MainTodayFragment()
            1 -> MainAllFragment()
            else -> null
        }!!
    }

    override fun getCount() = title.size

    override fun getPageTitle(position: Int) = title[position]

}