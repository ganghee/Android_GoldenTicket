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

    override fun getCount(): Int {
        return num_fragment
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }
}


/*override fun instantiateItem(container: ViewGroup, position: Int): Any {
    var view: View? = null
    view = inflater.inflate(R.layout.vp_lottery_confirm, null)
    view!!.tvShowName.text = dataList[position].title // TODO: 서버에서 받은 텍스트로 바꾸기
    view!!.tvCheckText.visibility= View.VISIBLE
    view!!.tvTime.text = dataList[position].time
    container.addView(view)
    return view
}
override fun isViewFromObject(p0: View, p1: Any): Boolean {
    return p0==p1
}
override fun getCount(): Int {
    return dataList.size; // TODO: 서버에서 전달받는 데이터 개수로 바꾸기
}
override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
    container.removeView(`object` as View)
}*/