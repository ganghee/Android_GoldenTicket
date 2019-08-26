package com.dazzi.goldenticket.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dazzi.goldenticket.R
import com.dazzi.goldenticket.adapter.viewholder.MonthShowDetailHolder
import com.dazzi.goldenticket.data.MonthShowDetailContentData
import com.dazzi.goldenticket.data.MonthShowDetailData


class MonthShowDetailAdapter: RecyclerView.Adapter<MonthShowDetailHolder>() {

    private val monthShowDetailContentList = mutableListOf<MonthShowDetailContentData>()

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int) =
        MonthShowDetailHolder(LayoutInflater.from(p0.context).inflate(R.layout.rv_main_month_show,p0,false))

    override fun getItemCount() = monthShowDetailContentList.size

    override fun onBindViewHolder(holder: MonthShowDetailHolder, position: Int) =
        holder.bind(monthShowDetailContentList[position])

    fun setData(setMonthShowDetailData: List<MonthShowDetailContentData>){
        monthShowDetailContentList.clear()
        monthShowDetailContentList.addAll(setMonthShowDetailData)
        notifyDataSetChanged()
    }
}