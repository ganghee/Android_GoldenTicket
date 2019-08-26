package com.dazzi.goldenticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.viewholder.MonthShowHolder
import com.dazzi.goldenticket.data.MonthShowListData

class MonthShowListAdapter : RecyclerView.Adapter<MonthShowHolder>() {

    private val monthShowList = mutableListOf<MonthShowListData>()

    override fun onCreateViewHolder(viewGroup: ViewGroup, p1: Int): MonthShowHolder =
        MonthShowHolder(
            LayoutInflater.from(viewGroup.context).inflate(
                R.layout.rv_main_month_list,
                viewGroup,
                false
            )
        )

    override fun getItemCount() = monthShowList.size

    override fun onBindViewHolder(holder: MonthShowHolder, position: Int) =
        holder.bind(monthShowList[position])

    fun setData(setMonthShowList: List<MonthShowListData>) {
        monthShowList.clear()
        monthShowList.addAll(setMonthShowList)
        notifyDataSetChanged()
    }
}