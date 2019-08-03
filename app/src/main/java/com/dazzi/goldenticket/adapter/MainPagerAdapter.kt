package com.dazzi.goldenticket.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.dazzi.goldenticket.fragment.MainAllFragment
import com.dazzi.goldenticket.fragment.MainTodayFragment

class MainPagerAdapter(fm: FragmentManager, private val num_fragment:Int): FragmentStatePagerAdapter(fm){

    private val title = arrayOf("Today","All")

    override fun getItem(p0: Int): Fragment {
        return when (p0) {
            0 -> MainTodayFragment()
            1 -> MainAllFragment()
            else -> null
        }!!
    }

    override fun getCount(): Int {
        return num_fragment

    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}